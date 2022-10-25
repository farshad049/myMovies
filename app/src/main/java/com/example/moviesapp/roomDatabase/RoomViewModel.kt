package com.example.moviesapp.roomDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.roomDatabase.Entity.FavoriteMovieEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    private val _movieListMutableLiveData = MutableLiveData<List<FavoriteMovieEntity>>()
    val movieListLiveData : LiveData<List<FavoriteMovieEntity>> = _movieListMutableLiveData

    private val _insertMovieMutableLiveData = MutableLiveData<Boolean>()
    val insertMovieLiveData : LiveData<Boolean> = _insertMovieMutableLiveData

    private val _deleteMovieMutableLiveData = MutableLiveData<Boolean>()
    val deleteMovieLiveData : LiveData<Boolean> = _deleteMovieMutableLiveData


     fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            repository.insertFavoriteMovie(movie)
            _insertMovieMutableLiveData.postValue(true)
        }
    }

    fun deleteFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            repository.deleteFavoriteMovie(movie)
            _deleteMovieMutableLiveData.postValue(true)
        }
    }

    fun getFavoriteMovieList(){
        viewModelScope.launch {
            repository.getAllFavoriteMovies().collectLatest {
                _movieListMutableLiveData.postValue(it)
            }
        }
    }







    private val _searchHistoryListMutableLiveData = MutableLiveData<List<SearchHistoryEntityWithoutId>>()
    val searchHistoryListLiveData : LiveData<List<SearchHistoryEntityWithoutId>> = _searchHistoryListMutableLiveData

    private val _insertSearchHistoryMutableLiveData = MutableLiveData<Boolean>()
    val insertSearchHistoryLiveData : LiveData<Boolean> = _insertSearchHistoryMutableLiveData

    private val _deleteSearchHistoryMutableLiveData = MutableLiveData<Boolean>()
    val deleteSearchHistoryLiveData : LiveData<Boolean> = _deleteSearchHistoryMutableLiveData

    private val _deleteAllSearchHistoryByIDMutableLiveData = MutableLiveData<Boolean>()
    val deleteAllSearchHistoryByIdLiveData : LiveData<Boolean> = _deleteAllSearchHistoryByIDMutableLiveData


    fun insertSearchHistory(movie : SearchHistoryEntity){
        viewModelScope.launch {
            repository.insertMovieSearchHistory(movie)
            _insertSearchHistoryMutableLiveData.postValue(true)
        }
    }

    fun deleteAllSearchHistory(){
        viewModelScope.launch {
            repository.deleteAllMovieSearchHistory()
            _deleteSearchHistoryMutableLiveData.postValue(true)
        }
    }

    fun deleteSearchHistoryByID(movieId:Int){
        viewModelScope.launch {
            repository.deleteMovieSearchById(movieId)
            _deleteAllSearchHistoryByIDMutableLiveData.postValue(true)
        }
    }

    fun getSearchHistoryList(){
        viewModelScope.launch {
            repository.getAllMovieSearchHistory().collectLatest {
                _searchHistoryListMutableLiveData.postValue(it)
            }
        }
    }




}