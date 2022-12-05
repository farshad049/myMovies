package com.farshad.moviesapp.ui.login.model

import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel

data class LoginUserModel(
    val userName: TextFieldStatusModel = TextFieldStatusModel.Success(),
    val password : TextFieldStatusModel = TextFieldStatusModel.Success(),
)
