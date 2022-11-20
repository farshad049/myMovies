package com.farshad.moviesapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    init {
        getFavoriteMovieList()
    }

    private val _favoriteMovieListMutableLiveData = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val favoriteMovieListMutableLiveData : StateFlow<List<FavoriteMovieEntity>> = _favoriteMovieListMutableLiveData

    private val _insertMovieMutableLiveData = MutableLiveData<Boolean>()
    val insertMovieLiveData : LiveData<Boolean> = _insertMovieMutableLiveData

    private val _deleteMovieMutableLiveData = MutableLiveData<Boolean>()
    val deleteMovieLiveData : LiveData<Boolean> = _deleteMovieMutableLiveData


    fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            roomRepository.insertFavoriteMovie(movie)
            _insertMovieMutableLiveData.postValue(true)
        }
    }

    fun deleteFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            roomRepository.deleteFavoriteMovie(movie)
            _deleteMovieMutableLiveData.postValue(true)
        }
    }

    private fun getFavoriteMovieList(){
        viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collectLatest {
                if (it.isEmpty()){
                    emptyList<FavoriteMovieEntity>()
                }else{
                    _favoriteMovieListMutableLiveData.value = it
                }

            }
        }
    }



}