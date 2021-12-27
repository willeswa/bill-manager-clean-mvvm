package app.monkpad.billmanager.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        //navigation
        val bottomNav = binding.bottomNavigationView
        val toolbar = binding.mainToolbar

        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)

        val appBarConfig = AppBarConfiguration(setOf(
                R.id.home_nav,
                R.id.new_nav,
                R.id.savings_nav))

        setupActionBarWithNavController(navController, appBarConfig)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            if(destination.id == R.id.home_nav){
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }

        }

    }


}