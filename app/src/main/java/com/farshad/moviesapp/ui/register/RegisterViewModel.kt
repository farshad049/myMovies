package com.farshad.moviesapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.model.network.RegisterResponseModel
import com.farshad.moviesapp.data.model.network.RegisterUserModel
import com.farshad.moviesapp.data.model.ui.RegisterFieldValidationModel
import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel
import com.farshad.moviesapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _registerUserLiveData = Channel<RegisterResponseModel?>()
    val registerUserLiveData = _registerUserLiveData.receiveAsFlow()

    private val _validationMutableLiveData= Channel<RegisterFieldValidationModel>()
    val validationLiveData = _validationMutableLiveData.receiveAsFlow()

    fun registerUser(user : RegisterUserModel){
        viewModelScope.launch {
            val response=repository.registerUser(user)
            _registerUserLiveData.send(response)
        }
    }



    fun validate(userName:String, email:String, password:String ) {
        val userNameB = userName.trim()
        val emailB = email.trim()
        val passwordB = password.trim()


        viewModelScope.launch {
            when{

                userNameB.isEmpty() -> {
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(
                            userName = TextFieldStatusModel.Error("please enter valid user name")
                        )
                    )
                }
                emailB.isEmpty() -> {
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(
                            email = TextFieldStatusModel.Error("please enter a valid email")
                        )
                    )
                }
                passwordB.isEmpty() -> {
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(
                            password = TextFieldStatusModel.Error("please enter a valid password")
                        )
                    )
                }

                else ->{
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(
                            userName = TextFieldStatusModel.Success(),
                            email  = TextFieldStatusModel.Success(),
                            password = TextFieldStatusModel.Success()

                        )
                    )
                    registerUser(
                        RegisterUserModel(
                            name = userNameB ,
                            email = emailB,
                            password =passwordB,
                        )
                    )

                }
            }
        }


    }



}