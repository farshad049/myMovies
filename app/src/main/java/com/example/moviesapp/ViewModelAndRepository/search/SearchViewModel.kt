package com.example.moviesapp.ViewModelAndRepository.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesapp.model.mapper.MovieMapper
import com.example.moviesapp.network.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper
    ):ViewModel() {



    private var currentUserSearch:String=""
    //every time pagingSource be called, this block of code will be run because every time user may type a new string to be searched
    private var pagingSource: SearchDataSource? =null
        get() {
            if (field == null || field?.invalid == true){
                field = SearchDataSource(apiClient,movieMapper,userSearch = currentUserSearch)
            }
            return field
        }

    val searchFlow = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 40,
            enablePlaceholders = false
        )
    ) { pagingSource!! }.flow.cachedIn(viewModelScope)



    fun submitQuery(userSearch:String){
        currentUserSearch=userSearch
        pagingSource?.invalidate()

    }

}