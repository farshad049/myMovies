package com.example.moviesapp.ViewModelAndRepository.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.ViewModelAndRepository.MovieDataSource
import com.example.moviesapp.ViewModelAndRepository.MovieRepository
import com.example.moviesapp.model.domain.DomainMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val movieDataSource: MovieDataSource,
) : ViewModel() {

    private val _firstPageMovieLiveData= MutableLiveData<List<DomainMovieModel?>>()
    val firstPageMovieLiveData: LiveData<List<DomainMovieModel?>> = _firstPageMovieLiveData

    fun getFirstPageMovie(){
        viewModelScope.launch {
            val response= repository.getFirstPageMovie()
            _firstPageMovieLiveData.postValue(response)
        }
    }



}