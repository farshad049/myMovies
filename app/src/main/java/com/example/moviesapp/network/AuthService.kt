package com.example.moviesapp.network

import com.example.moviesapp.model.network.UserAuthModel
import dagger.Provides
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import javax.inject.Singleton


interface AuthService:MovieService {

    @Multipart
    @POST("oauth/token")
     fun refreshTokenFromApi (@Part("refresh_token") username: RequestBody,
                              @Part("grant_type") grantType: RequestBody
    ): UserAuthModel




//     fun safeRefreshTokenFromApi(refreshToken:RequestBody,grantType:RequestBody):SimpleResponse<UserAuthModel>{
//        return safeApiCall { refreshTokenFromApi(refreshToken,grantType) }
//    }




    //run safe check for network issues
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }

}