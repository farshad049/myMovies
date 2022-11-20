package com.farshad.moviesapp.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.RoomRepository
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.ui.UiMovieDetailModel
import com.farshad.moviesapp.data.repository.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _movieByIdLiveData= MutableStateFlow<DomainMovieModel?>(DomainMovieModel())
    val movieByIdLiveData: StateFlow<DomainMovieModel?> =_movieByIdLiveData

    private val _movieByGenreLiveData= MutableStateFlow<List<DomainMovieModel>>(emptyList())
    val movieByGenreLiveData: StateFlow<List<DomainMovieModel>> = _movieByGenreLiveData

    private val _favoriteMovieListMutableLiveData = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val favoriteMovieListMutableLiveData : StateFlow<List<FavoriteMovieEntity>> = _favoriteMovieListMutableLiveData


    init {
        getFavoriteMovieList()
    }



    fun getMovieById(movieId: Int){
        viewModelScope.launch {
            val response=repository.getMovieById(movieId)
            _movieByIdLiveData.value=response

            if (response?.genres?.isNotEmpty() == true) {
                val genreId = genreNameToId(response.genres.component1())
                getMovieByGenre(genreId)
            }
        }
    }


    private fun  getMovieByGenre(genreId: Int){
        viewModelScope.launch {
            val response=repository.getMovieByGenre(genreId)
            _movieByGenreLiveData.value=response
        }
    }

    private fun getFavoriteMovieList(){
        viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collectLatest {
                _favoriteMovieListMutableLiveData.value = it
            }
        }
    }



    val combinedData =
        combine(
            movieByIdLiveData,
            favoriteMovieListMutableLiveData ,
            movieByGenreLiveData
        ){movieById , favoriteMovieList , similarMovieList ->
           UiMovieDetailModel(
                movie = movieById  ,
                isFavorite = favoriteMovieList.map { it.id }.contains(movieById?.id) ,
                similarMovies = similarMovieList
            )
        }
















    private fun genreNameToId(genreName:String?):Int{
        return when(genreName){
            "Crime" -> 1
            "Drama"-> 2
            "Action" -> 3
            "Biography" -> 4
            "History" -> 5
            "Adventure" -> 6
            "Fantasy" -> 7
            "Western" -> 8
            "Comedy" -> 9
            "Sci-Fi" -> 10
            "Mystery" -> 11
            "Thriller" -> 12
            "Family" -> 13
            "War" -> 14
            "Animation" -> 15
            "Romance" -> 16
            "Horror" -> 17
            "Music" -> 18
            "Film-Noir" -> 19
            "Musical" -> 20
            "Sport" ->21
            else -> 0
        }
    }


}