package com.farshad.moviesapp.data.network

import com.farshad.moviesapp.data.model.network.UserAuthModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface AuthService:MovieService {

    @Multipart
    @POST("oauth/token")
     suspend fun refreshTokenFromApi (@Part("refresh_token") username: RequestBody,
                              @Part("grant_type") grantType: RequestBody
    ): Response<UserAuthModel>
}


//class ApiAuth(private val authService: AuthService){
//
//    suspend fun safeRefreshTokenFromApi(refreshToken:RequestBody,grantType:RequestBody):SimpleResponse<UserAuthModel>{
//        return safeApiCall { authService.refreshTokenFromApi(refreshToken,grantType) }
//    }
//
//
//    //run safe check for network issues
//    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
//        return try {
//            SimpleResponse.success(apiCall.invoke())
//        } catch (e: Exception) {
//            SimpleResponse.failure(e)
//        }
//    }
//}