package com.farshad.moviesapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.FragmentSettingBinding
import com.farshad.moviesapp.ui.MainActivity
import com.farshad.moviesapp.util.BiometricAuthentication
import com.farshad.moviesapp.util.BiometricPrefManager
import com.farshad.moviesapp.util.LocaleManager
import com.farshad.moviesapp.util.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment: Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var biometricAuthentication: BiometricAuthentication

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var biometricManager: BiometricPrefManager


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



        val selectedLangBtn =
            when(localeManager.getLocale()){
            "en" -> R.id.radioButtonEnglish
            "fa" -> R.id.radioButtonFarsi
            else -> R.id.radioButtonEnglish
        }

        binding.radioGroupLanguage.check(selectedLangBtn)

        binding.radioButtonFarsi.setOnClickListener {
            localeManager.clearSharedPref()
            localeManager.saveLocale("fa")
            (activity as MainActivity).recreate()
        }

        binding.radioButtonEnglish.setOnClickListener {
            localeManager.clearSharedPref()
            localeManager.saveLocale("en")
            (activity as MainActivity).recreate()
        }











        val selectedThemeBtn =
            when(themeManager.getTheme()){
                "dark" -> R.id.radioButtonDark
                "system" -> R.id.radioButtonSystem
                else -> R.id.radioButtonLight
            }

        binding.radioGroupTheme.check(selectedThemeBtn)

        binding.radioButtonDark.setOnClickListener {
            themeManager.clearSharedPref()
            themeManager.saveTheme("dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.radioButtonSystem.setOnClickListener {
            themeManager.clearSharedPref()
            themeManager.saveTheme("system")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        binding.radioButtonLight.setOnClickListener {
            themeManager.clearSharedPref()
            themeManager.saveTheme(null)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }








        //set swFingerPrint is check status base on shared preference
        binding.swFingerPrint.isChecked = biometricManager.isBiometricLoginEnabled()
        //check if device supports the biometric functionality then set swFingerPrint enable
        checkDeviceHasBiometrics()

        binding.swFingerPrint.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                biometricAuthentication.promptForFragment(requireContext(),this){isAuthenticateSucceed->
                    if (isAuthenticateSucceed){// set saveBiometricStatus only if the process is complete
                        biometricManager.saveBiometricStatus(true)
                    }else{
                        binding.swFingerPrint.isChecked= false
                    }
                }
            }else{
                biometricAuthentication.promptForFragment(requireContext(),this){isAuthenticateSucceed->
                    if (isAuthenticateSucceed){
                        biometricManager.saveBiometricStatus(false)
                    }else{
                        binding.swFingerPrint.isChecked= true
                    }
                }

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