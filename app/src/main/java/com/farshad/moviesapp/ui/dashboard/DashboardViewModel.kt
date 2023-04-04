package com.farshad.moviesapp.ui.dashboard

import androidx.lifecycle.*
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.network.GenresModel
import com.farshad.moviesapp.data.model.ui.Resource
import com.farshad.moviesapp.ui.dashboard.model.UiMovieAndGenre
import com.farshad.moviesapp.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    init {
        getFirstPageMovie()
        getAllGenres()
    }

    private val _firstPageMovieFlow = MutableStateFlow<Resource<List<DomainMovieModel>>>(Resource.Loading)
    val firstPageMovieFlow= _firstPageMovieFlow.asStateFlow()

    private val _allGenresMovieFlow= MutableStateFlow<Resource<List<GenresModel>>>(Resource.Loading)
    val allGenresMovieFlow = _allGenresMovieFlow.asStateFlow()

    fun getFirstPageMovie(){
        viewModelScope.launch {
            val response= repository.getFirstPageMovie()
            if (response.isNotEmpty()) _firstPageMovieFlow.emit(Resource.Success(response))
        }
    }

    fun getAllGenres(){
        viewModelScope.launch {
            val response= repository.getAllGenres()
            if (response.isNotEmpty()) _allGenresMovieFlow.emit(Resource.Success(response))
        }
    }


    val combinedData : Flow<UiMovieAndGenre> =
            combine(
                firstPageMovieFlow,
                allGenresMovieFlow
            ){listOfMovie , listOfGenre ->
                if (listOfMovie is Resource.Success && listOfGenre is Resource.Success){
                    return@combine  UiMovieAndGenre(
                        movie = listOfMovie.data,
                        genre = listOfGenre.data
                    )
                }else{
                    return@combine UiMovieAndGenre(
                        emptyList(),
                        emptyList()
                    )
                }
            }




//    val topAndGenresLiveData: LiveData<Pair<List<DomainMovieModel?>, List<GenresModel?>>> =
//        object: MediatorLiveData<Pair<List<DomainMovieModel?>, List<GenresModel?>>>() {
//            var topMovies: List<DomainMovieModel?> = emptyList()
//            var genresList: List<GenresModel?> = emptyList()
//            init {
//                addSource(firstPageMovieLiveData) { top ->
//                    topMovies = top
//                   // genresList?.let { value = top to it }
//
//                    value = top to genresList
//                }
//                addSource(allGenresMovieLiveData) { genre ->
//                    genresList = genre
//                   // topMovies?.let { value = it to genre }
//                    value = topMovies to genre
//                }
//            }
//        }







}