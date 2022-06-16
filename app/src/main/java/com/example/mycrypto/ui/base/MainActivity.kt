package com.example.mycrypto.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mycrypto.R
import com.example.mycrypto.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(activityMainBinding.toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            activityMainBinding.drawerLayout,
            activityMainBinding.toolbar,
            0,0
        )
        activityMainBinding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.setToolbarNavigationClickListener {
            activityMainBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        toggle.syncState()

        activityMainBinding.bottomNavigation.setupWithNavController(navController)

        activityMainBinding.bottomNavigation.setOnItemReselectedListener {
            when(it.itemId){
                R.id.prices_menu -> {
                    navController.navigate(R.id.global_prices_fragment)
                }
                R.id.favorites_menu -> {
                    navController.navigate(R.id.global_favorites_fragment)
                }
            }
        }
    }

}