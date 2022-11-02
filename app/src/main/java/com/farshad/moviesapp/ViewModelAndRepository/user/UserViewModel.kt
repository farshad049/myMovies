package com.farshad.moviesapp.ViewModelAndRepository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.model.network.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
):ViewModel() {

    private val _registerUserLiveData = MutableLiveData<RegisterResponseModel?>()
    val registerUserLiveData: LiveData<RegisterResponseModel?> = _registerUserLiveData

    private val _loginUserLiveData = MutableLiveData<LoginResponseModel>()
    val loginUserLiveData: LiveData<LoginResponseModel> = _loginUserLiveData

    private val _userInfoLiveData = MutableLiveData<UserRegisteredModel?>()
    val userInfoLiveData: LiveData<UserRegisteredModel?> = _userInfoLiveData


    fun registerUser(user: RegisterUserModel){
        viewModelScope.launch {
            val response=repository.registerUser(user)
            _registerUserLiveData.postValue(response)
        }
    }

    fun loginUser(email: RequestBody, password: RequestBody, grantType: RequestBody){
        viewModelScope.launch {
            val response=repository.loginUser(email,password,grantType)
            _loginUserLiveData.postValue(response)
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            val response=repository.getUserInfo()
            _userInfoLiveData.postValue(response)
        }
    }







}