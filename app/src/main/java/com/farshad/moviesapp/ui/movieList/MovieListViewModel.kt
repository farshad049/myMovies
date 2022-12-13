package com.farshad.moviesapp.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.moviesapp.data.model.mapper.MovieMapper
import com.farshad.moviesapp.data.remote.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper,
):ViewModel() {


    var movieDataSource: MovieDataSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = MovieDataSource(apiClient,movieMapper)
            }
            return field
        }

    val movieListFlow = Pager(
        PagingConfig(
        pageSize = 10,
        prefetchDistance = 20,
        enablePlaceholders = false
    )
    ) { movieDataSource!! }.flow.cachedIn(viewModelScope)







}