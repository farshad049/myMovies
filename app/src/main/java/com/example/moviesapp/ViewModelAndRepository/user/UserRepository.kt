package com.example.moviesapp.ViewModelAndRepository.user

import android.util.Log
import com.example.moviesapp.model.network.RegisterUserModel
import com.example.moviesapp.model.network.UserAuthModel
import com.example.moviesapp.model.network.UserRegisteredModel
import com.example.moviesapp.model.ui.LoginResponseModel
import com.example.moviesapp.network.ApiClient
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun registerUser(user: RegisterUserModel):UserRegisteredModel?{
        val response= apiClient.registerUser(user)
        if (!response.isSuccessful){
            return null
        }
        return response.body
    }

    suspend fun loginUser(email: RequestBody, password: RequestBody, grantType: RequestBody): LoginResponseModel{
        val response=apiClient.loginUser(email,password,grantType)

        return if (response.isSuccessful){
            LoginResponseModel.Success(response.body)
        }else{
            val jsonObj = JSONObject(response.data?.errorBody()!!.charStream().readText()).getString("message")
            LoginResponseModel.Error(jsonObj)
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

fun getErrorMessage(raw: String): String{
    val object1 = JSONObject(raw)
    return object1.getString("message")
}