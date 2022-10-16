package com.example.moviesapp.ui

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.LOCALE_CODE
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = this.getSharedPreferences(Constants.PREFS_LOCALE_FILE, Context.MODE_PRIVATE)
        val localeLang: String? = prefs.getString(LOCALE_CODE  , null)
        val languageCode = if (localeLang?.isNotEmpty() == true) localeLang else "en"
        setLocale(this , languageCode)

        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //enable the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //enable the action bar
        appBarConfiguration= AppBarConfiguration(
//            navController.graph ,
            topLevelDestinationIds = setOf(
                R.id.dashboardFragment,
                R.id.movieList,
                R.id.submit,
                R.id.registerFragment
            ),
            drawerLayout = binding.drawerLayout
        )

        //set up fragment title in toolbar
        setupActionBarWithNavController(navController,appBarConfiguration)


        //enable navigation drawer
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)



        // Setup bottom nav bar
        val navBar=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(navBar, navController)












    }//FUN

    //enable back button on action bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //hide keyboard when touched outside
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    //change app locale
     private fun setLocale(activity: Activity = this, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }





}


