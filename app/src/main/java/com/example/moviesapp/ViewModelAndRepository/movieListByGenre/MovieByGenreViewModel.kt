package com.example.moviesapp.ViewModelAndRepository.movieListByGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesapp.ViewModelAndRepository.search.SearchDataSource
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.network.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieByGenreViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper
): ViewModel() {



    private var genreId:Int=0
    //every time pagingSource be called, this block of code will be run because every time user may type a new string to be searched
    private var pagingSource: MovieByGenreDataSource? =null
        get() {
            if (field == null || field?.invalid == true){
                field = MovieByGenreDataSource(apiClient,movieMapper, genreId = genreId)
            }
            return field
        }

    val movieByGenreFlow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 40,
            enablePlaceholders = false
        )
    ) { pagingSource!! }.flow.cachedIn(viewModelScope)



    fun submitQuery(genreIdFromFragment:Int){
        genreId=genreIdFromFragment
        pagingSource?.invalidate()

    }

}