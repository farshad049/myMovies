package com.example.moviesapp.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.moviesapp.MoviesApplication
import com.example.moviesapp.arch.SearchDataSource
import com.example.moviesapp.model.mapper.MovieMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://moviesapi.ir/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val duration = Duration.ofSeconds(30)
        val builder=OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        })
        builder.addInterceptor(
            ChuckerInterceptor.Builder(MoviesApplication.context)
                .collector(ChuckerCollector(MoviesApplication.context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )

        return builder.build()
//        return OkHttpClient.Builder()
//            .connectTimeout(duration)
//            .readTimeout(duration)
//            .writeTimeout(duration)
//            .build()
    }

    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }



    @Provides
    @Singleton
    fun providesApiClient(movieService: MovieService): ApiClient {
        return ApiClient(movieService)
    }






}