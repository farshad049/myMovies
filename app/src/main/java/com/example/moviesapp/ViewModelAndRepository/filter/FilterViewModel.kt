package com.example.moviesapp.ViewModelAndRepository.filter

import androidx.hilt.lifecycle.ViewModelInject
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


class FilterViewModel : ViewModel() {




    val _filterByGenreInfo1LiveData = MutableLiveData(
        FilterByGenreInfo1(
            genres = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
            selectedGenres = emptySet()
        )
    )

    val filterByGenreInfo1LiveData : LiveData<FilterByGenreInfo1> = _filterByGenreInfo1LiveData




    val _filterByImdbRateInfo1LiveData = MutableLiveData(
        FilterByImdbInfo1(
            imdbRate =  setOf( 9.0 , 8.5, 8.0 ),
            selectedImdbRate = emptySet()
        )
    )

    val filterByImdbRateInfo1LiveData : LiveData<FilterByImdbInfo1> = _filterByImdbRateInfo1LiveData












}