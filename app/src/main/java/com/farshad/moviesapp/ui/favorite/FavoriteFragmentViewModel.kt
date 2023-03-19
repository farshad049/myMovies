package com.farshad.moviesapp.ui.favorite

import android.util.Log
import androidx.lifecycle.*
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.RoomRepository
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.ui.Resource
import com.farshad.moviesapp.data.repository.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {


    private val _databaseFavoriteMovieListFlow = MutableStateFlow<Resource<List<FavoriteMovieEntity>>>(Resource.Loading)
    val databaseFavoriteMovieListFlow  = _databaseFavoriteMovieListFlow.asStateFlow()

    private val _insertMovieFlow = MutableStateFlow(false)
    val insertMovieFlow= _insertMovieFlow.asStateFlow()

    private val _deleteMovieFlow = MutableStateFlow(false)
    val deleteMovieFlow = _deleteMovieFlow.asStateFlow()

    private val _listOfFavoriteMovie= MutableStateFlow<Set<DomainMovieModel>>(emptySet())
    val listOfFavoriteMovie= _listOfFavoriteMovie.asStateFlow()


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

     fun getFavoriteMovieList()= viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collect {
                if (it.isEmpty()){
                    _databaseFavoriteMovieListFlow.emit(Resource.Loading)
                }else{
                    _databaseFavoriteMovieListFlow.emit(Resource.Success(it))
                }
            }
        }

    fun prepareFavoriteMovieList()= viewModelScope.launch {
        when (databaseFavoriteMovieListFlow.value) {
            is Resource.Loading -> {
                _listOfFavoriteMovie.emit(emptySet())
                return@launch
            }
            is Resource.Success -> {
                (databaseFavoriteMovieListFlow.value as Resource.Success<List<FavoriteMovieEntity>>).data.forEach{
                    val newMovieToAdd= favoriteRepository.getMovieById(it.id)
                    if (newMovieToAdd != null){
                        //create a list for new favorite fragment
                        val newFavoriteMovieList= listOfFavoriteMovie.value + newMovieToAdd
                        _listOfFavoriteMovie.emit(newFavoriteMovieList)
                        Log.e("NEWLIST", listOfFavoriteMovie.value.size.toString())
                    }
                }
            }
            else -> {}
        }
    }





}