package com.farshad.moviesapp.data.model.ui

data class SubmitFieldValidationModel (
    val title: TextFieldStatusModel = TextFieldStatusModel.Success(),
    val imdbId : TextFieldStatusModel = TextFieldStatusModel.Success(),
    val country : TextFieldStatusModel = TextFieldStatusModel.Success(),
    val year : TextFieldStatusModel = TextFieldStatusModel.Success(),
    val poster : TextFieldStatusModel = TextFieldStatusModel.Success()
        )


