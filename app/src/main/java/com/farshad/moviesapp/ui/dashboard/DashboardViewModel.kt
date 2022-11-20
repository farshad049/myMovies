package com.farshad.moviesapp.ui.dashboard

import androidx.lifecycle.*
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.network.GenresModel
import com.farshad.moviesapp.data.model.ui.UiMovieAndGenre
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

    private val _firstPageMovieLiveData= MutableLiveData<List<DomainMovieModel?>>()
    val firstPageMovieLiveData: LiveData<List<DomainMovieModel?>> = _firstPageMovieLiveData
    private val _firstPageMovieIsDone = MutableStateFlow(true)
    val firstPageMovieIsDone = _firstPageMovieIsDone.asStateFlow()

    private val _allGenresMovieLiveData= MutableLiveData<List<GenresModel>>()
    val allGenresMovieLiveData: LiveData<List<GenresModel>> = _allGenresMovieLiveData
    private val _allGenresMovieIsDone = MutableStateFlow(true)
    val allGenresMovieIsDone = _allGenresMovieIsDone.asStateFlow()

    fun getFirstPageMovie(){
        viewModelScope.launch {
            val response= repository.getFirstPageMovie()
            _firstPageMovieLiveData.postValue(response)
            if (response.isNotEmpty()) _firstPageMovieIsDone.value = false
        }
    }

    fun getAllGenres(){
        viewModelScope.launch {
            val response= repository.getAllGenres()
            _allGenresMovieLiveData.postValue(response)
            if (response.isNotEmpty()) _allGenresMovieIsDone.value = false
        }
    }


    val combinedData : Flow<UiMovieAndGenre> = combine(
        firstPageMovieLiveData.asFlow(),
        allGenresMovieLiveData.asFlow()
    ){listOfMovie , listOfGenre ->
        UiMovieAndGenre(
            movie = listOfMovie ,
            genre = listOfGenre
        )
    }



    val topAndGenresLiveData: LiveData<Pair<List<DomainMovieModel?>, List<GenresModel?>>> =
        object: MediatorLiveData<Pair<List<DomainMovieModel?>, List<GenresModel?>>>() {
            var topMovies: List<DomainMovieModel?> = emptyList()
            var genresList: List<GenresModel?> = emptyList()
            init {
                addSource(firstPageMovieLiveData) { top ->
                    topMovies = top
                   // genresList?.let { value = top to it }

                    value = top to genresList
                }
                addSource(allGenresMovieLiveData) { genre ->
                    genresList = genre
                   // topMovies?.let { value = it to genre }
                    value = topMovies to genre
                }
            }
        }







}