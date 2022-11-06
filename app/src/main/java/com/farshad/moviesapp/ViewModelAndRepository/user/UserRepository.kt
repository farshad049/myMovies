package com.farshad.moviesapp.ViewModelAndRepository.user


import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.model.network.LoginResponseModel
import com.farshad.moviesapp.model.network.RegisterResponseModel
import com.farshad.moviesapp.model.network.RegisterUserModel
import com.farshad.moviesapp.model.network.UserRegisteredModel
import com.farshad.moviesapp.network.ApiClient
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiClient: ApiClient ,
    private val tokenManager: TokenManager
    ) {


    suspend fun registerUser(user: RegisterUserModel): RegisterResponseModel {
        val response = apiClient.registerUser(user)

        return if (!response.isSuccessful){
            //val jsonObj = JSONObject(response.data?.errorBody()!!.charStream().readText()).getString("errors")
            val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("errors") }
            RegisterResponseModel.Error(jsonObj ?: "Something went wrong")
        }else{
            RegisterResponseModel.Success(response.body)
        }
    }


    suspend fun loginUser(email: RequestBody, password: RequestBody, grantType: RequestBody): LoginResponseModel {
        val response=apiClient.loginUser(email,password,grantType)

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


