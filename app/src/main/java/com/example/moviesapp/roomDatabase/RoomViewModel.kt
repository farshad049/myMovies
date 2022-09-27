package com.example.moviesapp.roomDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    private val _movieListMutableLiveData = MutableLiveData<List<MovieEntity>>()
    val movieListLiveData : LiveData<List<MovieEntity>> = _movieListMutableLiveData

    private val _insertMovieMutableLiveData = MutableLiveData<Boolean>()
    val insertMovieLiveData : LiveData<Boolean> = _insertMovieMutableLiveData

    private val _deleteMovieMutableLiveData = MutableLiveData<Boolean>()
    val deleteMovieLiveData : LiveData<Boolean> = _deleteMovieMutableLiveData


     fun insertMovie(movie : MovieEntity){
        viewModelScope.launch {
            repository.insertMovie(movie)
            _insertMovieMutableLiveData.postValue(true)
        }
    }

    fun deleteMovie(movie : MovieEntity){
        viewModelScope.launch {
            repository.deleteMovie(movie)
            _deleteMovieMutableLiveData.postValue(true)
        }
    }

    fun getMovieList(){
        viewModelScope.launch {
            repository.getAllMovies().collectLatest {
                _movieListMutableLiveData.postValue(it)
            }
        }
    }


}