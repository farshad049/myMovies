package com.farshad.moviesapp.ViewModelAndRepository.user


import android.util.Log
import com.farshad.moviesapp.MoviesApplication.Companion.context
import com.farshad.moviesapp.R
import com.farshad.moviesapp.model.network.*
import com.farshad.moviesapp.network.ApiClient
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiClient: ApiClient ) {


    suspend fun registerUser(user: RegisterUserModel): RegisterResponseModel {
        val response = apiClient.registerUser(user)

        return if (response.isSuccessful){
            RegisterResponseModel.Success(response.body)
        }else{
            val jsonObj = JSONObject(response.data?.errorBody()!!.charStream().readText()).getString("errors")
            RegisterResponseModel.Error(jsonObj)
        }
    }


    suspend fun loginUser(email: RequestBody, password: RequestBody, grantType: RequestBody): LoginResponseModel {
        val response=apiClient.loginUser(email,password,grantType)

//        return when{
//            response.data?.code() == 200 ->{
//                LoginResponseModel.Success(response.body)
//            }
//            response.data?.code() == 401 ->{
//                val jsonObj = response.data.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("message") }
//                LoginResponseModel.Error(jsonObj)
//            }
//            else -> {
//                LoginResponseModel.Loading
//            }
//        }

        return if (!response.isSuccessful){
            val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("message") }
            LoginResponseModel.Error(jsonObj)

        }else{
            LoginResponseModel.Success(response.body)
        }
    }


    suspend fun getUserInfo():UserRegisteredModel?{
        val response=apiClient.getUserInfo()
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }


}


