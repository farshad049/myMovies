package com.example.moviesapp.ViewModelAndRepository.filter

import androidx.lifecycle.*
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.ui.FilterByGenreInfo1
import com.example.moviesapp.model.ui.FilterByImdbInfo1
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    val store : Store<ApplicationState>,
    val repository: FilterRepository ,
    handle: SavedStateHandle
): ViewModel() {




    val _filterByGenreInfo1LiveData : MutableStateFlow<FilterByGenreInfo1> = MutableStateFlow(
        FilterByGenreInfo1(
            genres = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
            selectedGenres = emptySet()
        )
    )

    val filterByGenreInfo1LiveData = _filterByGenreInfo1LiveData.asStateFlow()




    val _filterByImdbRateInfo1LiveData :MutableStateFlow<FilterByImdbInfo1> = MutableStateFlow(
        FilterByImdbInfo1(
            imdbRate =  setOf( 9.0 , 8.5, 8.0 ),
            selectedImdbRate = emptySet()
        )
    )

    val filterByImdbRateInfo1LiveData = _filterByImdbRateInfo1LiveData.asStateFlow()











}