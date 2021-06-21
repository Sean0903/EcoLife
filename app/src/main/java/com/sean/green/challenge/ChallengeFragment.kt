package com.sean.green.challenge

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.sean.green.GreenApplication
import com.sean.green.MainActivity
import com.sean.green.NavigationDirections
import com.sean.green.R
import com.sean.green.databinding.FragmentChallengeBinding
import com.sean.green.ext.FORMAT_YYYY_MM_DDHHMMSS
import com.sean.green.ext.getVmFactory
import com.sean.green.ext.toDateFormat
import com.sean.green.login.UserManager
import java.io.*


class ChallengeFragment : Fragment() {

    private lateinit var binding: FragmentChallengeBinding

    private val viewModel by viewModels<ChallengeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChallengeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.imageChallengePageBackToHome.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToHomeFragment())
        }

        binding.imageChallengePageInfo.setOnClickListener {

            var saveDialog = Dialog(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_challenge, null)
            saveDialog.setContentView(view)
            saveDialog.show()

        }

        //need refactor
        binding.buttonChallengePageSave.setOnClickListener {

            if (viewModel.plastic.value.isNullOrBlank() &&
                viewModel.power.value.isNullOrBlank() &&
                viewModel.carbon.value.isNullOrBlank()
            ) {
                Toast.makeText(context, "請輸入挑戰", Toast.LENGTH_LONG).show()
            } else {
                viewModel.addChallengeData2Firebase(UserManager.user.email)
                Toast.makeText(context, "已成功送出", Toast.LENGTH_LONG).show()
            }

            if (viewModel.content.value != null) {
                viewModel.addArticle2Firebase(UserManager.user.email)
                Toast.makeText(context, "已成功送出", Toast.LENGTH_LONG).show()
            }
        }

        binding.addChallengePhoto.setOnClickListener{
            activateCamera()
        }

        return binding.root
    }

    private fun activateCamera() {
        getPermissions()
        if (isUploadPermissionsGranted) {
            selectImage()
        } else if (!isUploadPermissionsGranted) {

            Toast.makeText(
                GreenApplication.applicationContext(),
                GreenApplication.applicationContext()
                    .getString(R.string.edit_upload_permission_hint),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showGallery() {

        val intent = Intent()
        intent.type = GreenApplication.applicationContext()
            .getString(R.string.edit_show_gallery_intent_type)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent, GreenApplication.applicationContext()
                    .getString(R.string.edit_show_gallery_select_picture)
            ),
            IMAGE_FROM_GALLERY
        )
    }

    // Create an image file name
    private fun createImageFile(): File {

        //This is the directory in which the file will be created. This is the default location of Camera photos
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ),
            GreenApplication.applicationContext().getString(R.string.edit_start_camera_camera)
        )

        return File.createTempFile(
            viewModel.date.value.toDateFormat(FORMAT_YYYY_MM_DDHHMMSS),  /* prefix */
            GreenApplication.applicationContext()
                .getString(R.string.edit_start_camera_jpg), /* suffix */
            storageDir      /* directory */
        )
    }

    //handling the image chooser activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode !== Activity.RESULT_CANCELED) {


            if (resultCode == Activity.RESULT_OK) {

                when (requestCode) {
                    IMAGE_FROM_GALLERY -> {

                        data?.let {

                            it.data?.let { data ->

                                filePath = data

                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(
                                        (activity as MainActivity).contentResolver, filePath
                                    )

                                    val matrix = Matrix()
                                    matrix.postRotate(
                                        getImageRotation(
                                            GreenApplication.applicationContext(),
                                            data
                                        ).toFloat()
                                    )

                                    val outBitmap = Bitmap.createBitmap(
                                        bitmap!!, 0, 0,
                                        bitmap!!.width, bitmap!!.height, matrix, false
                                    )

                                    val byte = ByteArrayOutputStream()

                                    outBitmap.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        15,
                                        byte
                                    )

                                    val byteArray = byte.toByteArray()

                                    uploadFile(byteArray)

                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                    IMAGE_FROM_CAMERA -> {

                        fileFromCamera?.let {
                            Log.d("Sean","the value of file path =  $fileFromCamera")
                            bitmap = data?.extras?.get("data") as Bitmap

                            val matrix = Matrix()

                            val outBitmap = Bitmap.createBitmap(
                                bitmap!!, 0, 0,
                                bitmap!!.width, bitmap!!.height, matrix, false
                            )

                            val byte = ByteArrayOutputStream()

                            outBitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                15,
                                byte
                            )

                            val byteArray = byte.toByteArray()

                            uploadCamera(byteArray)
//                            }
                        }
                    }
                }
            }
        }
    }

    fun BitMaptoString(bitmap: Bitmap): String {
        val ByteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteStream)
        val b = ByteStream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun getImageRotation(context: Context, uri: Uri): Int {
        var stream: InputStream? = null
        return try {
            stream = context.contentResolver.openInputStream(uri)
            val exifInterface = ExifInterface(stream!!)
            val exifOrientation =
                exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            when (exifOrientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: Exception) {
            0
        } finally {
            stream?.close()
        }
    }

    fun selectImage() {

        val items = arrayOf<CharSequence>(
            GreenApplication.applicationContext().resources.getText(R.string.edit_choose_from_gallery),
            GreenApplication.applicationContext().resources.getText(R.string.edit_cancel)
        )

        val context = this.context

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(GreenApplication.applicationContext().resources.getText(R.string.edit_add_photo_title))
        builder.setItems(items) { dialog, item ->
            if (items[item] == GreenApplication.applicationContext().resources.getText(R.string.edit_cancel)) {

                dialog.dismiss()
            } else {

                chooseCameraOrGallery = items[item].toString()
                callCameraOrGallery()
            }
        }

        builder.show()
    }

    private fun callCameraOrGallery() {

        chooseCameraOrGallery?.let {

            when (it) {

                GreenApplication.applicationContext().resources.getString(R.string.edit_choose_from_gallery) -> {

                    showGallery()
                }
            }
        }

    }

    private fun uploadCamera(bitmap: ByteArray) {

        viewModel.uploadPhoto()

        UserManager.uid?.let { uid ->


            // Firebase storage
            auth = FirebaseAuth.getInstance()

            Log.d("Sean", "the value of date = ${viewModel.date.value}")

            val imageReference = FirebaseStorage.getInstance().reference.child(
                GreenApplication.applicationContext().getString(
                    R.string.firebase_storage_reference, uid, viewModel.date.value.toDateFormat(
                        FORMAT_YYYY_MM_DDHHMMSS
                    )
                )
            ).child(fileFromCamera.toString())

            imageReference.putBytes(bitmap)
                .addOnCompleteListener {


                    imageReference.downloadUrl.addOnCompleteListener { task ->

                        task.result?.let { taskResult ->

                            Log.d("Sean", "the result of pic = $taskResult")

                            viewModel.setPhoto(taskResult)
                        }
                    }
                }

        }
    }

    private fun uploadFile(bitmap: ByteArray) {
        filePath?.let { filePath ->

            viewModel.uploadPhoto()

            UserManager.uid?.let { uid ->

                // Firebase storage
                auth = FirebaseAuth.getInstance()

                Log.d("Sean", "the value of date = ${viewModel.date.value}")

                val imageReference = FirebaseStorage.getInstance().reference.child(
                    GreenApplication.applicationContext().getString(
                        R.string.firebase_storage_reference, uid, viewModel.date.value.toDateFormat(
                            FORMAT_YYYY_MM_DDHHMMSS
                        )
                    )
                ).child(filePath.toString())

                imageReference.putBytes(bitmap)
                    .addOnCompleteListener {


                        imageReference.downloadUrl.addOnCompleteListener { task ->

                            task.result?.let { taskResult ->

                                Log.d("Sean", "the result of pic = $taskResult")

                                viewModel.setPhoto(taskResult)
                            }
                        }
                    }

            }
        }
    }


    private fun compress(image: Uri): ByteArray? {

        var imageStream: InputStream? = null

        try {
            imageStream =
                GreenApplication.applicationContext().contentResolver.openInputStream(
                    image
                )
        } catch (e: FileNotFoundException) {

            e.printStackTrace()
        }

        val bitmapOrigin = BitmapFactory.decodeStream(imageStream)

        val stream = ByteArrayOutputStream()
        // 縮小至 15 %
        bitmapOrigin.compress(Bitmap.CompressFormat.JPEG, 15, stream)
        val byteArray = stream.toByteArray()

        try {

            stream.close()
            return byteArray
        } catch (e: IOException) {

            e.printStackTrace()
        }

        return null
    }

    fun getPermissions() {

        val permissions = arrayOf(
            PERMISSION_CAMERA,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
        )
        when (ContextCompat.checkSelfPermission(
            GreenApplication.applicationContext(),
            PERMISSION_CAMERA
        )) {

            PackageManager.PERMISSION_GRANTED -> {
                when (ContextCompat.checkSelfPermission(
                    GreenApplication.applicationContext(),
                    PERMISSION_WRITE_EXTERNAL_STORAGE
                )) {

                    PackageManager.PERMISSION_GRANTED -> {
                        when (ContextCompat.checkSelfPermission(
                            GreenApplication.applicationContext(),
                            PERMISSION_READ_EXTERNAL_STORAGE
                        )) {

                            PackageManager.PERMISSION_GRANTED -> {
                                isUploadPermissionsGranted = true
                            }
                        }
                    }

                    else -> {
                        ActivityCompat.requestPermissions(
                            activity as MainActivity, permissions,
                            SELECT_PHOTO_PERMISSION_REQUEST_CODE
                        )
                    }
                }
            }

            else -> {
                ActivityCompat.requestPermissions(
                    activity as MainActivity,
                    permissions,
                    SELECT_PHOTO_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        isUploadPermissionsGranted = false

        when (requestCode) {

            SELECT_PHOTO_PERMISSION_REQUEST_CODE ->

                if (grantResults.isNotEmpty()) {

                    for (i in 0 until grantResults.size) {

                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                            isUploadPermissionsGranted = false
                            return
                        }
                    }
                    isUploadPermissionsGranted = true
                    try {
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
        }
    }


    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(GreenApplication.applicationContext().packageManager) != null) {

            try {
                fileFromCamera = createImageFile()

                Log.d("EditFragment", "the value of return photo = $fileFromCamera")

            } catch (ex: IOException) {
                return
            }
            if (fileFromCamera != null) {
                startActivityForResult(intent, IMAGE_FROM_CAMERA)
            }
        }
    }


    companion object {
        private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE =
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            Manifest.permission.READ_EXTERNAL_STORAGE
        private const val SELECT_PHOTO_PERMISSION_REQUEST_CODE = 1234

        //Image request code
        private const val IMAGE_FROM_CAMERA = 0
        private const val IMAGE_FROM_GALLERY = 1
        private var chooseCameraOrGallery: String? = null

        //Uri to store the image uri
        private var filePath: Uri? = null

        //Bitmap to get image from gallery
        private var bitmap: Bitmap? = null
        private var auth: FirebaseAuth? = null
        private var displayMetrics: DisplayMetrics? = null
        private var windowManager: WindowManager? = null
        private var fileFromCamera: File? = null
        var isUploadPermissionsGranted = false
    }
}