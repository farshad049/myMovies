package com.example.moviesapp.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSettingBinding
import com.example.moviesapp.ui.BaseFragment
import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.util.BiometricAuthentication
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.IS_AUTHENTICATION_ENABLED
import com.example.moviesapp.util.Constants.LOCALE_CODE
import com.example.moviesapp.util.Constants.THEME_CODE
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment: BaseFragment(R.layout.fragment_setting) {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var biometricAuthentication: BiometricAuthentication


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val biometricPrefs: SharedPreferences = mainActivity.getSharedPreferences(Constants.PREFS_AUTHENTICATION_FILE, Context.MODE_PRIVATE)




        val languagePrefs: SharedPreferences = mainActivity.getSharedPreferences(Constants.PREFS_LOCALE_FILE, Context.MODE_PRIVATE)

        val selectedLangBtn =
            when(languagePrefs.getString(LOCALE_CODE  , null)){
            "en" -> R.id.radioButtonEnglish
            "fa" -> R.id.radioButtonFarsi
            else -> R.id.radioButtonEnglish
        }

        binding.radioGroupLanguage.check(selectedLangBtn)

        binding.radioButtonFarsi.setOnClickListener {
            languagePrefs.edit().clear().apply()
            languagePrefs.edit().putString(LOCALE_CODE,"fa").apply()

            val intent = Intent(requireActivity(), MainActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.radioButtonEnglish.setOnClickListener {
            languagePrefs.edit().clear().apply()
            languagePrefs.edit().putString(LOCALE_CODE,"en").apply()

            val intent = Intent(requireActivity(), MainActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }









        val themePrefs: SharedPreferences = mainActivity.getSharedPreferences(Constants.PREFS_THEME_FILE, Context.MODE_PRIVATE)

        val selectedThemeBtn =
            when(themePrefs.getString(THEME_CODE  , null)){
                "dark" -> R.id.radioButtonDark
                "system" -> R.id.radioButtonSystem
                else -> R.id.radioButtonLight
            }

        binding.radioGroupTheme.check(selectedThemeBtn)

        binding.radioButtonDark.setOnClickListener {
            themePrefs.edit().clear().apply()
            themePrefs.edit().putString(THEME_CODE , "dark").apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.radioButtonSystem.setOnClickListener {
            themePrefs.edit().clear().apply()
            themePrefs.edit().putString(THEME_CODE , "system").apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        binding.radioButtonLight.setOnClickListener {
            themePrefs.edit().clear().apply()
            themePrefs.edit().putString(THEME_CODE , null).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }








        //set swFingerPrint is check status base on shared preference
        binding.swFingerPrint.isChecked = biometricPrefs.getBoolean(IS_AUTHENTICATION_ENABLED , true)
        //check if device supports the biometric functionality
        checkDeviceHasBiometrics()

        binding.swFingerPrint.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                biometricAuthentication.promptForFragment(requireContext(),this)
                biometricPrefs.edit().putBoolean(IS_AUTHENTICATION_ENABLED , true).apply()
            }else{
                biometricPrefs.edit().putBoolean(IS_AUTHENTICATION_ENABLED , false).apply()
            }
        }



























    }//FUN

    private fun checkDeviceHasBiometrics(){
            val biometricManager = BiometricManager.from(requireContext())
            when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    binding.swFingerPrint.isEnabled = true
                    Log.i("MY_APP_TAG", "App can authenticate using biometrics.")
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Log.i("MY_APP_TAG", "No biometric features available on this device.")
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Log.i("MY_APP_TAG", "Biometric features are currently unavailable.")
                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> Log.i("MY_APP_TAG", "unknown.")
                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> Log.i("MY_APP_TAG", "unsupported.")
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> Log.i("MY_APP_TAG", "security update.")
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Log.i("MY_APP_TAG", "enroll")
                    // Prompts the user to create credentials that your app accepts.
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                    }
                    startActivityForResult(enrollIntent, 100)
                }
            }
        }











    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}