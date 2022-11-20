package com.farshad.moviesapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.db.Entity.SearchHistoryEntity
import com.farshad.moviesapp.data.db.Entity.SearchHistoryEntityWithoutId
import com.farshad.moviesapp.data.db.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

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