package com.example.moviesapp.network

import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.model.network.UserAuthModel
import com.example.moviesapp.util.Constants.BASE_URL
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor() : Authenticator {

    @Inject lateinit var tokenManager: TokenManager

    override fun authenticate(route: Route?, response: Response): Request? {


        return runBlocking {

            val refreshToken=tokenManager.getRefreshToken()
            val refreshTokenR:RequestBody= refreshToken?.toRequestBody() ?: "".toRequestBody()
            val grantTypeR:RequestBody= "refresh_token".toRequestBody()

            //val newAccessToken = authService.safeRefreshTokenFromApi(refreshToken,grantType)
            val newAccessToken = getUpdatedToken(refreshTokenR,grantTypeR)

            tokenManager.saveToken(newAccessToken.body()) // save new access_token for next called

            newAccessToken.body()?.let {
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.access_token}") // just only need to override "Authorization" header, don't need to override all header since this new request is create base on old request
                    .build()
            }
        }

    }




    private suspend fun getUpdatedToken( refreshToken:RequestBody,grantType:RequestBody): retrofit2.Response<UserAuthModel> {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


//        val service :AuthService by lazy{
//            retrofit.create(AuthService::class.java) }
//        val apiAuth=ApiAuth(service)
//        return apiAuth.safeRefreshTokenFromApi(refreshToken,grantType)

        val service=retrofit.create(AuthService::class.java)
        return service.refreshTokenFromApi(refreshToken,grantType)


    }
}