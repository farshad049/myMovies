package com.farshad.moviesapp.ui.movieListByGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.moviesapp.data.model.mapper.MovieMapper
import com.farshad.moviesapp.data.network.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieByGenreViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper
): ViewModel() {



    private var genreId:Int=0
    //every time pagingSource be called, this block of code will be run because every time user may type a new string to be searched
     var pagingSource: MovieByGenreDataSource? =null
        get() {
            if (field == null || field?.invalid == true){
                field = MovieByGenreDataSource(apiClient,movieMapper, genreId = genreId)
            }
            return field
        }

    val movieByGenreFlow = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 20,
            enablePlaceholders = false
        )
    ) { pagingSource!! }.flow.cachedIn(viewModelScope)



    fun submitQuery(genreIdFromFragment:Int){
        genreId=genreIdFromFragment
        pagingSource?.invalidate()

    }

}