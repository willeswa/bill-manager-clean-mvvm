package app.monkpad.billmanager.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.ActivityMainBinding
import app.monkpad.billmanager.utils.Utility.Companion.styleToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        val bottomNav = binding.bottomNavigationView
        val toolbar = binding.mainToolbar

        //Setup support action manually instead of in theme
        //Gives us more flexibility with other screens.
        //Note, we would ref toolbar directly but then nav bottom wouldn't work
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)


        //The menu items have to have the same ids as the destinations
        //for the bottom nav to setupwithnavcontroller
        val appBarConfig = AppBarConfiguration(setOf(
                R.id.home_nav,
                R.id.savings_nav))

        setupActionBarWithNavController(navController, appBarConfig)

        navController.addOnDestinationChangedListener{_, destination,_ ->
            styleToolbar(destination, toolbar, bottomNav, this)
        }

    }

    //using the actionBar (refer to line 33 c.) forces us to override
    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }


}