package com.farshad.moviesapp.ui.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.model.network.*
import com.farshad.moviesapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: UserRepository
):ViewModel() {

    init {
        getUserInfo()
    }


    private val _userInfoLiveData = MutableLiveData<UserRegisteredModel?>()
    val userInfoLiveData: LiveData<UserRegisteredModel?> = _userInfoLiveData



    private fun getUserInfo(){
        viewModelScope.launch {
            val response=repository.getUserInfo()
            _userInfoLiveData.postValue(response)
        }
    }



}