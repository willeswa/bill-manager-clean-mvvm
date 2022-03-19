package app.monkpad.billmanager.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.ActivityMainBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.presentation.homescreen.HomeScreenViewModel
import app.monkpad.billmanager.utils.Utility.styleToolbar
//import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: HomeScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MobileAds.initialize(this) {}

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
        viewModel = ViewModelProvider(this,
            BillManagerViewModelFactory).get(HomeScreenViewModel::class.java)

        //The menu items have to have the same ids as the destinations
        //for the bottom nav to setupwithnavcontroller
        val appBarConfig = AppBarConfiguration(navController.graph, binding.mainDrawerLayout)

//        setupActionBarWithNavController(navController, appBarConfig)
        toolbar.setupWithNavController(navController, appBarConfig)
        binding.navDrawer.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            styleToolbar(destination, toolbar, bottomNav, this)
            if (destination.id == R.id.home_nav) {
                viewModel.onHomeNavItemClicked()
            }
        }

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                toolbar.visibility = View.GONE
                bottomNav.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
                bottomNav.visibility = View.VISIBLE
            }
        })

        binding.navDrawer.setNavigationItemSelectedListener {
            binding.mainDrawerLayout.close()
            val playLink = getString(R.string.app_playstore_link)
            when (it.itemId) {
                R.id.drawer_action_rating -> {
                    val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse(playLink))
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                    true
                }
                R.id.drawer_action_share -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    val title = getString(R.string.share_title)
                    val text =
                        "Checkout this new cool app I am using to track my bills \uD83D\uDE0A: $playLink"
                    intent.putExtra(Intent.EXTRA_TEXT, text)
                    intent.type = "text/plain"
                    val chooser = Intent.createChooser(intent, title)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(chooser)
                    }
                    true
                }
                R.id.drawer_action_feedback -> {
                    val subject = getString(R.string.feedback_title)
                    val intent = Intent(Intent.ACTION_VIEW)
                    val data = Uri.parse("mailto:gwiliez@gmail.com?subject=$subject")
                    intent.data = data
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }

        }

    }

    //using the actionBar (refer to line 33 c.) forces us to override
    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}