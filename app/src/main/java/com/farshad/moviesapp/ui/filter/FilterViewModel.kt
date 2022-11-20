package com.farshad.moviesapp.ui.filter

import androidx.lifecycle.ViewModel
import com.farshad.moviesapp.data.model.ui.*
import com.farshad.moviesapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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


    val _expandItemsMutableLiveData = MutableStateFlow( UiExpandModel() )
    val expandItemsMutableLiveData : StateFlow<UiExpandModel> = _expandItemsMutableLiveData






    val combinedDataForFilterEpoxy =
        combine(
            filterByGenreInfoLiveData ,
            filterByImdbRateInfoLiveData ,
            expandItemsMutableLiveData
        ) { setOfGenresFilter, setOfImdbFilter , setOfExpandedItems ->

            val genreData = DataForFilterFragmentEpoxy.IsExpandAndList(
                isExpand = setOfExpandedItems.setOfExpandIds.contains(Constants.GENRE) ,
                filterList = setOfGenresFilter.genres.map { genres ->
                    UiFilter(
                        filterDisplayName = genres,
                        isSelected = setOfGenresFilter.selectedGenres.contains(genres)
                    )
                }
            )


            val imdbData = DataForFilterFragmentEpoxy.IsExpandAndList(
                isExpand = setOfExpandedItems.setOfExpandIds.contains(Constants.IMDBRATE) ,
                filterList = setOfImdbFilter.imdbRate.map { imdbRate ->
                    UiFilter(
                        filterDisplayName = imdbRate,
                        isSelected = setOfImdbFilter.selectedImdbRate.contains(imdbRate)
                    )
                }
            )


            return@combine DataForFilterFragmentEpoxy(genreData , imdbData)

        }





    val combinedDataForCarouselFilterMovieList =
        combine(
            filterByGenreInfoLiveData ,
            filterByImdbRateInfoLiveData
        ){genreSelectedFilters , imdbRateSelectedFilters ->
            genreSelectedFilters.selectedGenres +
                    imdbRateSelectedFilters.selectedImdbRate
        }





    val combinedFilterDataForMovieList =
        combine(
            filterByGenreInfoLiveData ,
            filterByImdbRateInfoLiveData
        ){genreSetOfSelectedFilters , imdbSetOfSelectedFilters ->
            ModelDataForMovieList(
                genreSetOfSelectedFilters = genreSetOfSelectedFilters.selectedGenres ,
                imdbSetOfSelectedFilters = imdbSetOfSelectedFilters.selectedImdbRate
            )
        }

















}