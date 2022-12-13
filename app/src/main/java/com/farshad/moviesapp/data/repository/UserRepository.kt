package com.farshad.moviesapp.data.repository


import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.ui.login.model.LoginResponseModel
import com.farshad.moviesapp.ui.register.model.RegisterResponseModel
import com.farshad.moviesapp.data.model.network.RegisterPostBody
import com.farshad.moviesapp.data.model.network.UserRegisteredModel
import com.farshad.moviesapp.data.remote.ApiClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiClient: ApiClient ,
    private val tokenManager: TokenManager
    ) {


    suspend fun registerUser(user: RegisterPostBody): RegisterResponseModel {
        val response = apiClient.registerUser(user)

        return if (!response.isSuccessful){
            //val jsonObj = JSONObject(response.data?.errorBody()!!.charStream().readText()).getString("errors")
            val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("errors") }
            RegisterResponseModel.Error(jsonObj ?: "Something went wrong")
        }else{
            RegisterResponseModel.Success(response.body)
        }
    }


    suspend fun loginUser(userName: String, password: String): LoginResponseModel {

        val userNameBody :RequestBody= userName.toRequestBody()
        val passwordBody: RequestBody = password.toRequestBody()
        val grantTypeBody: RequestBody = "password".toRequestBody()

        val response=apiClient.loginUser(userNameBody,passwordBody,grantTypeBody)

        return if (!response.isSuccessful){
            val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("message") }
            LoginResponseModel.Error(jsonObj ?: "Something went wrong")
        }else{
            tokenManager.saveToken(response.body)
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


