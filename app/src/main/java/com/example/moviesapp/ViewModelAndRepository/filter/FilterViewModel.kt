package com.example.moviesapp.ViewModelAndRepository.filter

import androidx.lifecycle.ViewModel
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    val store : Store<ApplicationState>
): ViewModel() {



}