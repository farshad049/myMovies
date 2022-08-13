package com.example.moviesapp.Authentication



import android.content.Context
import android.content.Intent
import com.example.moviesapp.MainActivity
import com.example.moviesapp.MoviesApplication.Companion.context
import com.example.moviesapp.model.network.UserAuthModel
import com.example.moviesapp.network.AuthService
import com.example.moviesapp.util.Constants.BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
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
            val refreshTokenB:RequestBody= refreshToken?.toRequestBody() ?: "".toRequestBody()
            val grantTypeB:RequestBody= "refresh_token".toRequestBody()

            val newAccessToken = getUpdatedToken(refreshTokenB,grantTypeB)

            if (!newAccessToken.isSuccessful){
                tokenManager.clearSharedPref()
                val intent=Intent(context,MainActivity::class.java)
                context.startActivity(intent)
            }

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