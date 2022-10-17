package com.example.moviesapp.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSettingBinding
import com.example.moviesapp.ui.BaseFragment
import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.LOCALE_CODE
import com.example.moviesapp.util.Constants.THEME_CODE


class SettingFragment:BaseFragment(R.layout.fragment_setting) {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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















    }//FUN









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}