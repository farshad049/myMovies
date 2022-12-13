package com.farshad.moviesapp.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.db.RoomRepository
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.ui.movieDetail.model.UiMovieDetailModel
import com.farshad.moviesapp.data.repository.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _movieByIdFlow= MutableStateFlow<DomainMovieModel?>(null)
    val movieByIdFlow = _movieByIdFlow.asStateFlow()

    private val _movieByGenreFlow= MutableStateFlow<List<DomainMovieModel>>(emptyList())
    val movieByGenreFlow = _movieByGenreFlow.asStateFlow()

    private val _favoriteMovieListFlow = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val favoriteMovieListFlow = _favoriteMovieListFlow.asStateFlow()



    fun getMovieById(movieId: Int){
        viewModelScope.launch {
            val response=repository.getMovieById(movieId)
            _movieByIdFlow.emit(response)

            if (response?.genres?.isNotEmpty() == true) {
                val genreId = genreNameToId(response.genres.component1())
                getMovieByGenre(genreId)
            }
        }
    }


    private fun  getMovieByGenre(genreId: Int){
        viewModelScope.launch {
            val response=repository.getMovieByGenre(genreId)
            _movieByGenreFlow.emit(response)
        }
    }

     fun getFavoriteMovieList(){
        viewModelScope.launch {
            roomRepository.getAllFavoriteMovies().collectLatest {
                _favoriteMovieListFlow.emit(it)
            }
        }
    }



    val combinedData =
        combine(
            movieByIdFlow,
            favoriteMovieListFlow ,
            movieByGenreFlow
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