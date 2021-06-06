package com.sean.green

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sean.green.data.Save
import com.sean.green.databinding.ActivityMainBinding
import com.sean.green.ext.getVmFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    private var isFABOpen: Boolean = false

    private lateinit var binding: ActivityMainBinding
    //toolbar
//    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    //drawer
//    private lateinit var appBarConfiguration: AppBarConfiguration

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.homeFragment -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToHomeFragment(
//                    FirebaseAuth.getInstance().currentUser!!.uid
                ))
                return@OnNavigationItemSelectedListener true
            }
            R.id.pagerFragment -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToPagerFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.calendarFragment -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToCalendarFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.shareFragment -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToShareFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.eventFragment -> {

                findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToEventFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

        binding.fabSave.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToSaveFragment(
            ))
            binding.fabShadow.visibility = View.GONE
            closeFABMenu()
        }

        binding.fabUse.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToUseFragment(
            ))
            binding.fabShadow.visibility = View.GONE
            closeFABMenu()
        }

        binding.fabChallenge.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToChallengeFragment(
            ))
            binding.fabShadow.visibility = View.GONE
            closeFABMenu()
        }

        binding.fabShare.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToToShareFragment(
            ))
            binding.fabShadow.visibility = View.GONE
            closeFABMenu()
        }


        setupBottomNav()
    }



    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

//        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
//        val itemView = menuView.getChildAt(2) as BottomNavigationItemView

    }

    private fun showFABMenu() {
        when (fabLayout_save.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_save.visibility = View.INVISIBLE
            else -> fabLayout_save.visibility = View.VISIBLE
        }
        when (fabLayout_use.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_use.visibility = View.INVISIBLE
            else -> fabLayout_use.visibility = View.VISIBLE
        }
        when (fabLayout_challenge.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_challenge.visibility = View.INVISIBLE
            else -> fabLayout_challenge.visibility = View.VISIBLE
        }
        when (fabLayout_share.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_challenge.visibility = View.INVISIBLE
            else -> fabLayout_share.visibility = View.VISIBLE
        }
        isFABOpen = true
        fabLayout_share.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fabLayout_challenge.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        fabLayout_use.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        fabLayout_save.animate().translationY(-resources.getDimension(R.dimen.standard_205))
        fab.animate().rotation(45.0f)
        fab_custom_pic.animate().rotation(45.0f)
        binding.fab.visibility = View.VISIBLE
        binding.fabShadow.visibility = View.VISIBLE

    }

    fun closeFABMenu() {

        fab_save.visibility = View.VISIBLE
        fab_use.visibility = View.VISIBLE
        fab_challenge.visibility = View.VISIBLE
        fab_share.visibility = View.VISIBLE

        when (fabLayout_save.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_save.visibility = View.INVISIBLE
            else -> fabLayout_save.visibility = View.VISIBLE
        }
        when (fabLayout_use.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_use.visibility = View.INVISIBLE
            else -> fabLayout_use.visibility = View.VISIBLE
        }
        when (fabLayout_challenge.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_challenge.visibility = View.INVISIBLE
            else -> fabLayout_challenge.visibility = View.VISIBLE
        }
        when (fabLayout_share.y){
            resources.getDimension(R.dimen.standard_0) -> fabLayout_challenge.visibility = View.INVISIBLE
            else -> fabLayout_challenge.visibility = View.VISIBLE
        }

        isFABOpen = false
        binding.fabShadow.visibility = View.GONE
        fab.animate().rotation(90.0f)
        fab_custom_pic.animate().rotation(90.0f)
        fabLayout_save.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_use.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_challenge.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_share.animate().translationY(resources.getDimension(R.dimen.standard_0))

    }

    fun dismissFabButton(set: Boolean) {
        when (set) {
            true -> {
                binding.fab.visibility = View.VISIBLE
            }
            false -> {
                binding.fab.visibility = View.GONE

            }
        }
    }

    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        // [END auth_sign_out]
    }

}