package com.example.moviesapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.ui.setting.SettingFragment
import com.example.moviesapp.util.BiometricAuthentication
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.LOCALE_CODE
import com.example.moviesapp.util.Constants.THEME_CODE
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    @Inject
    lateinit var biometricAuthentication: BiometricAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //shared preferences for language setting
        val languagePrefs: SharedPreferences = this.getSharedPreferences(Constants.PREFS_LOCALE_FILE, Context.MODE_PRIVATE)
        val localeRecord: String? = languagePrefs.getString(LOCALE_CODE  , "en")
        if(localeRecord?.isNotEmpty() == true) setLocale(this , localeRecord)


        //shared preferences for theme setting
        val themePrefs: SharedPreferences = this.getSharedPreferences(Constants.PREFS_THEME_FILE, Context.MODE_PRIVATE)
        val themeRecord: String? = themePrefs.getString(THEME_CODE  , "light")
        if (themeRecord?.isNotEmpty() == true) setDayNightTheme(themeRecord)



        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //enable the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //enable the action bar
        appBarConfiguration= AppBarConfiguration(
            //navController.graph ,
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


    private fun setDayNightTheme(themeRecord : String?){
        when(themeRecord){
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    override fun onResume() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
            //check if biometric authentication in enabled
            val biometricPrefs: SharedPreferences = this.getSharedPreferences(Constants.PREFS_AUTHENTICATION_FILE, Context.MODE_PRIVATE)
            if (biometricPrefs.getBoolean(Constants.IS_AUTHENTICATION_ENABLED, true)){
                biometricAuthentication.promptForActivity(this,this)
            }
        }

        super.onResume()
    }








}


