package com.farshad.moviesapp.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.model.network.RegisterPostBody
import com.farshad.moviesapp.data.repository.UserRepository
import com.farshad.moviesapp.ui.register.model.RegisterFieldValidationModel
import com.farshad.moviesapp.ui.register.model.RegisterResponseModel
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

    fun registerUser(user : RegisterPostBody){
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
                        RegisterFieldValidationModel(userName = "please enter valid user name")
                    )
                    return@launch
                }
                emailB.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(email = "please enter a valid email")
                    )
                    return@launch
                }
                passwordB.isEmpty() -> {
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(password = "please enter a valid password")
                    )
                    return@launch
                }

                else ->{
                    _validationMutableLiveData.send(
                        RegisterFieldValidationModel(
                            userName = null,
                            email = null,
                            password = null
                        )
                    )

                    registerUser(
                        RegisterPostBody(
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