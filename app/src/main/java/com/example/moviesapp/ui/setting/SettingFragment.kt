package com.example.moviesapp.ui.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSettingBinding
import com.example.moviesapp.ui.BaseFragment
import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.LOCALE_CODE
import java.util.*


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

        val prefs: SharedPreferences = mainActivity.getSharedPreferences(Constants.PREFS_LOCALE_FILE, Context.MODE_PRIVATE)

        val selectedLangBtn =
            when(prefs.getString(LOCALE_CODE  , null)){
            "en" -> R.id.radioButtonEnglish
            "fa" -> R.id.radioButtonFarsi
            else -> R.id.radioButtonEnglish
        }

        binding.radioGroup.check(selectedLangBtn)



        binding.radioButtonFarsi.setOnClickListener {
            prefs.edit().clear().apply()
            prefs.edit().putString(LOCALE_CODE,"fa").apply()

            val intent = Intent(requireActivity(), MainActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.radioButtonEnglish.setOnClickListener {
            prefs.edit().clear().apply()
            prefs.edit().putString(LOCALE_CODE,"en").apply()

            val intent = Intent(requireActivity(), MainActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

        }








    }//FUN









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}