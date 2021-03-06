package com.example.moviesapp.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesapp.model.domain.DomainMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository,private val movieDataSource: MovieDataSource) : ViewModel() {

    private val _movieByIdLiveData= MutableLiveData<DomainMovieModel?>()
    val movieByIdLiveData: LiveData<DomainMovieModel?> =_movieByIdLiveData

    private val _movieByGenreLiveData= MutableLiveData<List<DomainMovieModel?>>()
    val movieByGenreLiveData: LiveData<List<DomainMovieModel?>> = _movieByGenreLiveData

    fun fetchMovie(movieId: Int){
        viewModelScope.launch {
            val response=repository.getMovieById(movieId)
            _movieByIdLiveData.postValue(response)
        }
    }

    fun  fetchMovieByGenre(genreId: Int){
        viewModelScope.launch {
            val response=repository.getMovieByGenre(genreId)
            _movieByGenreLiveData.postValue(response)
        }
    }


    val flow = Pager(PagingConfig(
            pageSize = 20,
            prefetchDistance = 40,
            //so we don't have to be worried about nulls in view layer
            enablePlaceholders = false
        )
    ) { movieDataSource }.flow.cachedIn(viewModelScope)


}