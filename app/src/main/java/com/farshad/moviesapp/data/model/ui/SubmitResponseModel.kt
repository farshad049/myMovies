package com.farshad.moviesapp.data.model.ui

sealed interface SubmitResponseModel {
    data class Error(val message: String) : SubmitResponseModel
    data class Success(val data: UploadMovieModel) : SubmitResponseModel
    data class Loading(val loadingMsg: String) : SubmitResponseModel
}