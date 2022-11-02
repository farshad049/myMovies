package com.farshad.moviesapp.Authentication



import com.farshad.moviesapp.model.network.UserAuthModel
import com.farshad.moviesapp.network.AuthService
import com.farshad.moviesapp.util.Constants.BASE_URL
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
              //  val intent= Intent(context, MainActivity::class.java)
              //  intent.flags = FLAG_ACTIVITY_NEW_TASK
             //   context.startActivity(intent)
            }else{
                tokenManager.saveToken(newAccessToken.body()) // save new access_token for next call
            }


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