package com.sean.green

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sean.green.databinding.ActivityMainBinding
import com.sean.green.ext.getVmFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    private var isFabOpen: Boolean = false

    private lateinit var binding: ActivityMainBinding

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {

                    findNavController(R.id.fragment_main_activity).navigate(
                        NavigationDirections.navigateToHomeFragment(
                        )
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.pagerFragment -> {

                    findNavController(R.id.fragment_main_activity).navigate(NavigationDirections.navigateToPagerFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.calendarFragment -> {

                    findNavController(R.id.fragment_main_activity).navigate(NavigationDirections.navigateToCalendarFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.shareFragment -> {

                    findNavController(R.id.fragment_main_activity).navigate(NavigationDirections.navigateToShareFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.eventFragment -> {

                    findNavController(R.id.fragment_main_activity).navigate(NavigationDirections.navigateToEventFragment())
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
            if (!isFabOpen) {
                showFabMenu()
            } else {
                closeFabMenu()
            }
        }

        binding.fabSave.setOnClickListener {
            findNavController(R.id.fragment_main_activity).navigate(
                NavigationDirections.navigateToSaveFragment(
                )
            )
            binding.fabShadow.visibility = View.GONE
            closeFabMenu()
        }

        binding.fabUse.setOnClickListener {
            findNavController(R.id.fragment_main_activity).navigate(
                NavigationDirections.navigateToUseFragment(
                )
            )
            binding.fabShadow.visibility = View.GONE
            closeFabMenu()
        }

        binding.fabChallenge.setOnClickListener {
            findNavController(R.id.fragment_main_activity).navigate(
                NavigationDirections.navigateToChallengeFragment(
                )
            )
            binding.fabShadow.visibility = View.GONE
            closeFabMenu()
        }

        binding.fabShare.setOnClickListener {
            findNavController(R.id.fragment_main_activity).navigate(
                NavigationDirections.navigateToToShareFragment(
                )
            )
            binding.fabShadow.visibility = View.GONE
            closeFabMenu()
        }

        binding.fabEvent.setOnClickListener {
            findNavController(R.id.fragment_main_activity).navigate(
                NavigationDirections.navigateToToEventFragment(
                )
            )
            binding.fabShadow.visibility = View.GONE
            closeFabMenu()
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun showFabMenu() {
        text_save_title.visibility = View.VISIBLE
        text_use_title.visibility = View.VISIBLE
        text_challenge_title.visibility = View.VISIBLE
        text_share_title.visibility = View.VISIBLE
        text_event_title.visibility = View.VISIBLE
        when (fabLayout_save.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_save.visibility = View.INVISIBLE
            else -> fabLayout_save.visibility = View.VISIBLE
        }
        when (fabLayout_use.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_use.visibility = View.INVISIBLE
            else -> fabLayout_use.visibility = View.VISIBLE
        }
        when (fabLayout_challenge.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_challenge.visibility =
                View.INVISIBLE
            else -> fabLayout_challenge.visibility = View.VISIBLE
        }
        when (fabLayout_share.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_share.visibility =
                View.INVISIBLE
            else -> fabLayout_share.visibility = View.VISIBLE
        }
        when (fabLayout_event.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_event.visibility =
                View.INVISIBLE
            else -> fabLayout_event.visibility = View.VISIBLE
        }
        isFabOpen = true
        fabLayout_share.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fabLayout_event.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        fabLayout_use.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        fabLayout_save.animate().translationY(-resources.getDimension(R.dimen.standard_205))
        fabLayout_challenge.animate().translationY(-resources.getDimension(R.dimen.standard_255))
        fab.animate().rotation(45.0f)
        fab_custom_pic.animate().rotation(45.0f)
        binding.fab.visibility = View.VISIBLE
        binding.fabShadow.visibility = View.VISIBLE
    }

    fun closeFabMenu() {

        fab_save.visibility = View.VISIBLE
        fab_use.visibility = View.VISIBLE
        fab_challenge.visibility = View.VISIBLE
        fab_share.visibility = View.VISIBLE
        fab_event.visibility = View.VISIBLE
        text_save_title.visibility = View.INVISIBLE
        text_use_title.visibility = View.INVISIBLE
        text_challenge_title.visibility = View.INVISIBLE
        text_share_title.visibility = View.INVISIBLE
        text_event_title.visibility = View.INVISIBLE

        when (fabLayout_save.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_save.visibility = View.INVISIBLE
            else -> fabLayout_save.visibility = View.VISIBLE
        }
        when (fabLayout_use.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_use.visibility = View.INVISIBLE
            else -> fabLayout_use.visibility = View.VISIBLE
        }
        when (fabLayout_challenge.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_challenge.visibility =
                View.INVISIBLE
            else -> fabLayout_challenge.visibility = View.VISIBLE
        }
        when (fabLayout_share.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_share.visibility =
                View.INVISIBLE
            else -> fabLayout_share.visibility = View.VISIBLE
        }
        when (fabLayout_event.y) {
            resources.getDimension(R.dimen.standard_0) -> fabLayout_event.visibility =
                View.INVISIBLE
            else -> fabLayout_event.visibility = View.VISIBLE
        }

        isFabOpen = false
        binding.fabShadow.visibility = View.GONE
        fab.animate().rotation(90.0f)
        fab_custom_pic.animate().rotation(90.0f)
        fabLayout_save.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_use.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_challenge.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_share.animate().translationY(resources.getDimension(R.dimen.standard_0))
        fabLayout_event.animate().translationY(resources.getDimension(R.dimen.standard_0))
    }

    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        // [END auth_sign_out]
    }

}