package com.farshad.moviesapp.ui



import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ActivityMainBinding
import com.farshad.moviesapp.ui.dashboard.DashboardViewModel
import com.farshad.moviesapp.ui.userInfo.UserInfoViewModel
import com.farshad.moviesapp.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dashboardViewModel : DashboardViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    private lateinit var connectionLiveData: CheckInternetConnection

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var biometricAuthentication: BiometricAuthentication

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var biometricManager: BiometricPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set locale
        if (localeManager.getLocale() != null) setLocale(this, localeManager.getLocale()!!)


        //set theme
        if (themeManager.getTheme() != null) setDayNightTheme(themeManager.getTheme())


        installSplashScreen().apply {
            setKeepOnScreenCondition{
                //will stay on splash screen as long as firstPageMovieIsDone or allGenresMovieIsDone is true
                dashboardViewModel.firstPageMovieIsDone.value  && dashboardViewModel.allGenresMovieIsDone.value
            }
        }



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
        //setupActionBarWithNavController(navController,appBarConfiguration)


        //enable navigation drawer
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)



        // Setup bottom nav bar
        val navBar=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(navBar, navController)






        //set the button on toolbar to open the drawer
        binding.included.btnDrawerLayout.setOnClickListener {
           binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        //set edit text on toolbar to navigate to search fragment
        binding.included.edToolbarSearchBox.setOnClickListener {
            navController.navigateUp()
            navController.navigate(R.id.searchFragment)
        }

        //log out button on drawer layout
        binding.btnLogOutDrawerLayout.setOnClickListener {
            tokenManager.clearSharedPref()
            startActivity(Intent(this,MainActivity::class.java))
        }





        //check internet connection
        connectionLiveData = CheckInternetConnection(this)
        connectionLiveData.observe(this) { isNetworkAvailable ->
            isNetworkAvailable?.let {
                binding.tvNoInternetConnection.isVisible = !it

                if (!it) Toast.makeText(this,getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show()

            }
        }



        //show favorite movie tab or register tab on bottom navigation
        //show or hide user info in drawer
        //show or hide log out button on drawer layout
        //show or hide user name in drawer header
        val userNameInDrawerHeader = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvUserNameDrawerLayout)

        if (tokenManager.isLoggedIn()){
            binding.bottomNavigation.menu.findItem(R.id.registerFragment).isVisible = false
            binding.bottomNavigation.menu.findItem(R.id.favoriteFragment).isVisible = true
            binding.navView.menu.findItem(R.id.registerFragment).isVisible = false
            binding.navView.menu.findItem(R.id.userInfoFragment).isVisible = true
            binding.btnLogOutDrawerLayout.isVisible = true


            userInfoViewModel.userInfoLiveData.observe(this){
                userNameInDrawerHeader.text = "welcome ${it?.name}"
            }
            userNameInDrawerHeader.isVisible = true

        }else{
            binding.bottomNavigation.menu.findItem(R.id.registerFragment).isVisible = true
            binding.bottomNavigation.menu.findItem(R.id.favoriteFragment).isVisible = false
            binding.navView.menu.findItem(R.id.registerFragment).isVisible = true
            binding.navView.menu.findItem(R.id.userInfoFragment).isVisible = false
            binding.btnLogOutDrawerLayout.isVisible = false
            userNameInDrawerHeader.isVisible = false
        }










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
            if (biometricManager.isBiometricLoginEnabled()){
                biometricAuthentication.promptForActivity(this,this)
            }
        }
        super.onResume()
    }


    //use this to show or hide toolbar in fragments
    fun hideToolbar(isHide : Boolean){
        if (isHide) binding.toolbar.visibility = View.GONE else binding.toolbar.visibility = View.VISIBLE
    }








}


