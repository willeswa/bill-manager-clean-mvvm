package app.monkpad.billmanager.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//import com.google.android.gms.ads.MobileAds

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MobileAds.initialize(this) {}

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        val bottomNav = binding.bottomNavigationView

        //Setup support action manually instead of in theme
        //Gives us more flexibility with other screens.
        //Note, we would ref toolbar directly but then nav bottom wouldn't work


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)

    }

    //using the actionBar (refer to line 33 c.) forces us to override
    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

    fun getBottomNavbar() = binding.bottomNavigationView

}