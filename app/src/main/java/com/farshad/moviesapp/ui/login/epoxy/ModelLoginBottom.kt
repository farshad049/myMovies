package com.farshad.moviesapp.ui.login.epoxy

import android.content.Context
import android.util.Patterns
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelBottomLoginFragmentBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel


data class ModelLoginBottom(
    val context : Context,
    val onLogin: (String, String) -> Unit,
): ViewBindingKotlinModel<ModelBottomLoginFragmentBinding>(R.layout.model_bottom_login_fragment){

    override fun ModelBottomLoginFragmentBinding.bind() {

        btnLogin.setOnClickListener {
            val email =etEditLoginEmail.text?.toString()
            val password =etEditLoginPassword.text?.toString()

            when{
                email.isNullOrBlank() || !(Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
                    etLoginEmail.error = context.getString(R.string.enter_a_username)
                    return@setOnClickListener
                }
                password.isNullOrBlank() -> {
                    etLoginPassword.error = context.getString(R.string.enter_your_password)
                    return@setOnClickListener
                }
                else -> onLogin(email , password)
            }
        }

    }

    override fun ModelBottomLoginFragmentBinding.unbind() {
        etEditLoginEmail.text = null
        etEditLoginPassword.text = null
    }




}
