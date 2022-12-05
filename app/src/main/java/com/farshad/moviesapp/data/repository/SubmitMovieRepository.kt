package com.farshad.moviesapp.data.repository

import com.farshad.moviesapp.ui.submitMovie.model.UploadMovieModel
import com.farshad.moviesapp.data.network.ApiClient
import com.farshad.moviesapp.ui.submitMovie.model.SubmitResponseModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class SubmitMovieRepository @Inject constructor(
    private val apiClient: ApiClient,
    ){

    suspend fun pushMovieBase64(movie: UploadMovieModel): SubmitResponseModel {
        val response= apiClient.pushMovieBase64(movie)

        return if (!response.isSuccessful){
            val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("errors") }
            SubmitResponseModel.Error(jsonObj ?: "Something went wrong")
        }else{
            SubmitResponseModel.Success(response.body)
        }

    }



    suspend fun pushMovieMultipart(
        poster : MultipartBody.Part?,
        title : String,
        imdbId : String,
        country : String,
        year : String ,
    ): SubmitResponseModel {

        val titleBody : RequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val imdbIdBody : RequestBody = imdbId.toRequestBody("text/plain".toMediaTypeOrNull())
        val yearBody : RequestBody = year.toRequestBody("text/plain".toMediaTypeOrNull())
        val countryBody : RequestBody = country.toRequestBody("text/plain".toMediaTypeOrNull())

        val response= apiClient.pushMovieMulti(poster,titleBody,imdbIdBody,countryBody,yearBody)

        return if (!response.isSuccessful){
            val jsonObj = response.data?.errorBody()?.charStream()?.readText()?.let { JSONObject(it).getString("errors") }
            SubmitResponseModel.Error(jsonObj ?: "Something went wrong")
        }else{
            SubmitResponseModel.Success(response.body)
        }
    }















}