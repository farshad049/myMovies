package com.farshad.moviesapp.data.model.ui



sealed interface TextFieldStatusModel {
    data class Success(val data: String? = null) : TextFieldStatusModel
    data class Error(val error: String? = null) : TextFieldStatusModel
    object Loading : TextFieldStatusModel
}