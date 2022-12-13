package com.farshad.moviesapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {


    private val _favoriteMovieListFlow = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val favoriteMovieListFlow  = _favoriteMovieListFlow.asStateFlow()

    private val _insertMovieFlow = MutableStateFlow(false)
    val insertMovieFlow= _insertMovieFlow.asStateFlow()

    private val _deleteMovieFlow = MutableStateFlow(false)
    val deleteMovieFlow = _deleteMovieFlow.asStateFlow()


    fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            roomRepository.insertFavoriteMovie(movie)
            _insertMovieFlow.emit(true)
        }
    }

    fun deleteFavoriteMovie(movie : FavoriteMovieEntity){
        viewModelScope.launch {
            roomRepository.deleteFavoriteMovie(movie)
            _deleteMovieFlow.emit(true)
        }
    }

     fun getFavoriteMovieList(){
        viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collect {
                _favoriteMovieListFlow.emit(it)
            }
        }
    }



}