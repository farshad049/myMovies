package com.example.moviesapp.ViewModelAndRepository.dashboard

import androidx.lifecycle.*
import com.example.moviesapp.ViewModelAndRepository.MovieDataSource
import com.example.moviesapp.ViewModelAndRepository.MovieRepository
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.network.GenresModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val movieDataSource: MovieDataSource,
) : ViewModel() {

    private val _firstPageMovieLiveData= MutableLiveData<List<DomainMovieModel?>>()
    val firstPageMovieLiveData: LiveData<List<DomainMovieModel?>> = _firstPageMovieLiveData

    private val _allGenresMovieLiveData= MutableLiveData<List<GenresModel?>>()
    val allGenresMovieLiveData: LiveData<List<GenresModel?>> = _allGenresMovieLiveData

    fun getFirstPageMovie(){
        viewModelScope.launch {
            val response= repository.getFirstPageMovie()
            _firstPageMovieLiveData.postValue(response)
        }
    }

    fun getAllGenres(){
        viewModelScope.launch {
            val response= repository.getAllGenres()
            _allGenresMovieLiveData.postValue(response)
        }
    }



    val topAndGenresLiveData: LiveData<Pair<List<DomainMovieModel?>, List<GenresModel?>>> =
        object: MediatorLiveData<Pair<List<DomainMovieModel?>, List<GenresModel?>>>() {
            var topMovies: List<DomainMovieModel?>? = emptyList()
            var genresList: List<GenresModel?>? = emptyList()
            init {
                addSource(firstPageMovieLiveData) { yek ->
                    this.topMovies = yek
                    genresList?.let { value = yek to it }
                }
                addSource(allGenresMovieLiveData) { genre ->
                    this.genresList = genre
                    topMovies?.let { value = it to genre }
                }
            }
        }





}