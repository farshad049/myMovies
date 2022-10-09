package com.example.moviesapp.ViewModelAndRepository.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.ui.FilterByGenreInfo1
import com.example.moviesapp.model.ui.FilterByImdbInfo1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {




    val _filterByGenreInfo1LiveData = MutableStateFlow(
        FilterByGenreInfo1(
            genres = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
            selectedGenres = emptySet()
        )
    )

    val filterByGenreInfo1LiveData : StateFlow<FilterByGenreInfo1> = _filterByGenreInfo1LiveData




    val _filterByImdbRateInfo1LiveData = MutableStateFlow(
        FilterByImdbInfo1(
            imdbRate =  setOf( 9.0 , 8.5, 8.0 ),
            selectedImdbRate = emptySet()
        )
    )

    val filterByImdbRateInfo1LiveData : StateFlow<FilterByImdbInfo1> = _filterByImdbRateInfo1LiveData












}