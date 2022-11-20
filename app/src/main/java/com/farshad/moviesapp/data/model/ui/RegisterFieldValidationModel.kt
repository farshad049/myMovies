package com.farshad.moviesapp.data.model.ui

data class RegisterFieldValidationModel(
    val email: TextFieldStatusModel = TextFieldStatusModel.Success(),
    val password : TextFieldStatusModel = TextFieldStatusModel.Success(),
    val userName : TextFieldStatusModel = TextFieldStatusModel.Success(),
)
