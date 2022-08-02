package com.example.moviesapp.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.moviesapp.MoviesApplication
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit (): Retrofit {
        val authenticator = AuthAuthenticator(buildTokenApi())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(providesOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    fun providesOkHttpClient(interceptor: AuthInterceptor,authenticator: Authenticator? = null): OkHttpClient {
        val duration = Duration.ofSeconds(30)
        return OkHttpClient.Builder()
            .connectTimeout(duration)
            .readTimeout(duration)
            .also { client -> authenticator?.let { client.authenticator(it) }}
            .writeTimeout(duration)
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })

            .addInterceptor(
            ChuckerInterceptor.Builder(MoviesApplication.context)
                .collector(ChuckerCollector(MoviesApplication.context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )

            .addInterceptor(interceptor)

            .build()
    }

//    @Provides
//    @Singleton
//    fun providesMovieService(retrofit: Retrofit): MovieService {
//        return retrofit.create(MovieService::class.java)
//    }



    @Provides
    @Singleton
    fun providesApiClient(movieService: MovieService): ApiClient {
        return ApiClient(movieService)
    }








    private fun buildTokenApi(): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(providesOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }






}