package com.farshad.moviesapp.ViewModelAndRepository.filter

import androidx.lifecycle.ViewModel
import com.farshad.moviesapp.model.ui.FilterByGenreInfo
import com.farshad.moviesapp.model.ui.FilterByImdbInfo
import com.farshad.moviesapp.model.ui.UiExpandModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {




    val _filterByGenreInfoLiveData = MutableStateFlow(
        FilterByGenreInfo(
            genres = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
            selectedGenres = emptySet()
        )
    )

    val filterByGenreInfoLiveData : StateFlow<FilterByGenreInfo> = _filterByGenreInfoLiveData




    val _filterByImdbRateInfo1LiveData = MutableStateFlow(
        FilterByImdbInfo(
            imdbRate =  setOf( "9.0" , "8.5", "8.0" ),
            selectedImdbRate = emptySet()
        )
    )

    val filterByImdbRateInfoLiveData : StateFlow<FilterByImdbInfo> = _filterByImdbRateInfo1LiveData


    val _expandItemsMutableLiveData = MutableStateFlow(
        UiExpandModel()
    )
    val expandItemsMutableLiveData : StateFlow<UiExpandModel> = _expandItemsMutableLiveData














}