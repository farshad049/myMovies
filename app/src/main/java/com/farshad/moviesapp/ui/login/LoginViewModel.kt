package com.farshad.moviesapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.ui.login.model.LoginResponseModel
import com.farshad.moviesapp.ui.login.model.LoginUserModel
import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel
import com.farshad.moviesapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _loginUserLiveData = Channel<LoginResponseModel>()
    val loginUserLiveData = _loginUserLiveData.receiveAsFlow()


    private val _validationMutableLiveData= Channel<LoginUserModel>()
    val validationLiveData = _validationMutableLiveData.receiveAsFlow()



     fun loginUser(email: String, password: String){
        viewModelScope.launch {
            val response=repository.loginUser(email,password)
            _loginUserLiveData.send(response)
        }
    }



    fun validate(
        userName : String,
        password : String,
    )=
        viewModelScope.launch {

            val title = userName.trim()
            val imdbId = password.trim()

            when{
                title.isEmpty() -> {
                    _validationMutableLiveData.send(
                        LoginUserModel(
                            userName = TextFieldStatusModel.Error("please enter username")
                        )
                    )

                }
                imdbId.isEmpty() -> {
                    _validationMutableLiveData.send(
                        LoginUserModel(
                            password = TextFieldStatusModel.Error("please enter password")
                        )
                    )
                }

                else -> {
                    _validationMutableLiveData.send(
                        LoginUserModel(
                            userName = TextFieldStatusModel.Success(),
                            password = TextFieldStatusModel.Success(),
                        )
                    )

                    loginUser(userName , password)
                }
            }

        }








}