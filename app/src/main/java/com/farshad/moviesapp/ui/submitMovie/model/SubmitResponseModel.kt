package com.farshad.moviesapp.ui.submitMovie.model

sealed interface SubmitResponseModel {
    data class Error(val message: String) : SubmitResponseModel
    data class Success(val data: UploadMovieModel) : SubmitResponseModel
    data class Loading(val loadingMsg: String) : SubmitResponseModel
}