package com.okamiko.e_market.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.okamiko.e_market.R
import com.okamiko.e_market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
    }

    private fun setupActionBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.productListFragment, R.id.basketFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupWithNavController(binding.bottomNavigation, navController)

        supportActionBar?.title = getString(R.string.app_name)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = getString(R.string.app_name)
            when (destination.id) {
                com.okamiko.feature.R.id.productDetailFragment -> binding.bottomNavigation.visibility =
                    View.GONE

                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}