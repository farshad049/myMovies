package com.farshad.moviesapp.ui.login.epoxy

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ui.MainActivity
import com.farshad.moviesapp.ui.login.LoginViewModel
import com.farshad.moviesapp.ui.login.model.LoginResponseModel
import java.util.*


class LoginEpoxyController(
    private val context: Context,
    private val viewModel : LoginViewModel
) : TypedEpoxyController<LoginResponseModel>() {

    override fun buildModels(data: LoginResponseModel) {

//        ModelLoginBottom(
//            context = context ,
//            onLogin = {username , password ->
//                viewModel.loginUser(username , password)
//            }
//        ).id(UUID.randomUUID().toString()).addTo(this)
//
//
//        if (data is LoginResponseModel.Error){
//            Toast.makeText(context, data.error,Toast.LENGTH_SHORT).show()
//        }
//
//        if (data is LoginResponseModel.Success){
//            context.startActivity(Intent(context , MainActivity::class.java))
//            Toast.makeText(context, R.string.you_are_logged_in,Toast.LENGTH_SHORT).show()
//        }
    }



}