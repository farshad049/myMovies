package com.farshad.moviesapp.ui.favorite

import android.util.Log
import androidx.lifecycle.*
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.RoomRepository
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.ui.Resource
import com.farshad.moviesapp.data.repository.FavoriteMovieRepository
import com.farshad.moviesapp.ui.favorite.model.ListAndSelectedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {



    private val _insertMovieFlow = MutableStateFlow(false)
    val insertMovieFlow= _insertMovieFlow.asStateFlow()

    private val _deleteMovieFlow = MutableStateFlow(false)
    val deleteMovieFlow = _deleteMovieFlow.asStateFlow()

    private val _listOfFavoriteMovie= MutableStateFlow<Resource<List<DomainMovieModel>>>(Resource.Loading)
    val listOfFavoriteMovie= _listOfFavoriteMovie.asStateFlow()

    private val _selectedItem= MutableStateFlow<DomainMovieModel?>(null)
    val selectedItem= _selectedItem.asStateFlow()


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
            roomRepository.getAllFavoriteMovies().collect {list->
                if (list.isEmpty()){
                    _listOfFavoriteMovie.emit(Resource.Failure("NO-DATA"))
                }else{
                    prepareFavoriteMovieList(list)
                }
            }
        }

    // making a new list of DomainMovieModel out of a list of FavoriteMovieEntity
    private fun prepareFavoriteMovieList(list: List<FavoriteMovieEntity>){
        val newList = mutableListOf<DomainMovieModel>()
        viewModelScope.launch {
            list.forEach { databaseItem->
                val response= favoriteRepository.getMovieById(databaseItem.id)
                if (response != null) {
                    newList.add(response)
                }
            }
            _listOfFavoriteMovie.emit(Resource.Success(newList))
        }
    }

    fun changeSelectedItem(item: DomainMovieModel)= viewModelScope.launch{
        _selectedItem.emit(item)
    }


    val combinedData: Flow<Resource<ListAndSelectedData>> =
        combine(
            listOfFavoriteMovie,
            selectedItem
        ){list, selectedItem->

            return@combine when(list){
                is Resource.Loading -> Resource.Loading
                is Resource.Failure -> Resource.Failure("NO-DATA")
                is Resource.Success ->
                    Resource.Success(
                        ListAndSelectedData(
                            list = list.data,
                            selectedItem = selectedItem ?: list.data.component1()
                        )
                    )
            }
        }








}