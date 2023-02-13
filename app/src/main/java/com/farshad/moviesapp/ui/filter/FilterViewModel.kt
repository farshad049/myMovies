package com.farshad.moviesapp.ui.filter

import androidx.lifecycle.ViewModel
import com.farshad.moviesapp.ui.filter.model.*
import com.farshad.moviesapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {



    val _filterByGenreInfoMutableFlow = MutableStateFlow(
        FilterByGenreInfo(
            genres = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
                "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
            selectedGenres = emptySet()
        )
    )

    val filterByGenreInfoFlow = _filterByGenreInfoMutableFlow.asStateFlow()




    val _filterByImdbRateInfoFlow = MutableStateFlow(
        FilterByImdbInfo(
            imdbRate =  setOf( "9.0" , "8.5", "8.0" ),
            selectedImdbRate = emptySet()
        )
    )

    val filterByImdbRateInfoFlow = _filterByImdbRateInfoFlow.asStateFlow()


    val _expandItemsMutableFlow = MutableStateFlow( UiExpandModel() )
    val expandItemsMutableFlow = _expandItemsMutableFlow.asStateFlow()






    val combinedDataForFilterEpoxy =
        combine(
            filterByGenreInfoFlow ,
            filterByImdbRateInfoFlow ,
            expandItemsMutableFlow
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
            filterByGenreInfoFlow ,
            filterByImdbRateInfoFlow
        ){genreSelectedFilters , imdbRateSelectedFilters ->
            genreSelectedFilters.selectedGenres +
                    imdbRateSelectedFilters.selectedImdbRate
        }





    val combinedFilterDataForMovieList =
        combine(
            filterByGenreInfoFlow ,
            filterByImdbRateInfoFlow
        ){genreSetOfSelectedFilters , imdbSetOfSelectedFilters ->
            ModelDataForMovieList(
                genreSetOfSelectedFilters = genreSetOfSelectedFilters.selectedGenres ,
                imdbSetOfSelectedFilters = imdbSetOfSelectedFilters.selectedImdbRate
            )
        }

















}