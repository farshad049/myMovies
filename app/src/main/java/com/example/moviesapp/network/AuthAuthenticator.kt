package com.example.moviesapp.network

import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.model.network.UserAuthModel
import com.example.moviesapp.util.Constants.BASE_URL
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.Authenticator
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(private val AuthService:AuthService) : Authenticator {

    @Inject lateinit var tokenManager: TokenManager

    override fun authenticate(route: Route?, response: Response): Request? {

        return runBlocking {
            val refreshToken:RequestBody= tokenManager.getRefreshToken()?.toRequestBody() ?: "".toRequestBody()
            val grantType:RequestBody= "refresh_token".toRequestBody()

            //val newAccessToken = authService.safeRefreshTokenFromApi(refreshToken,grantType)
            val newAccessToken = getUpdatedToken(refreshToken,grantType)
            tokenManager.saveToken(newAccessToken.body()) // save new access_token for next called

            newAccessToken.body()?.let {
                response.request.newBuilder()
                    .header("Authorization", it.access_token) // just only need to override "Authorization" header, don't need to override all header since this new request is create base on old request
                    .build()
            }
        }

    }


    private suspend fun getUpdatedToken( refreshToken:RequestBody,grantType:RequestBody): UserAuthModel {
        val okHttpClient = OkHttpClient().newBuilder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


        val service = retrofit.create(AuthService::class.java)
        return service.refreshTokenFromApi(refreshToken,grantType)

    }
}