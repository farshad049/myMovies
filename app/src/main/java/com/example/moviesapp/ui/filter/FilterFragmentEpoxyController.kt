package com.example.moviesapp.ui.filter

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.Typed2EpoxyController
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.epoxy.FilterEpoxyModel
import com.example.moviesapp.epoxy.FilterImdbEpoxyModel
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.model.ui.UiGenreFilter
import com.example.moviesapp.model.ui.UiImdbRateFilter
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel
):Typed2EpoxyController<List<UiGenreFilter>,List<UiImdbRateFilter>>() {

    override fun buildModels(data1: List<UiGenreFilter>, data2: List<UiImdbRateFilter>) {

        HeaderEpoxyModel("Genres").id("filter_base_on_genres").addTo(this)

        data1.forEach {
            FilterEpoxyModel(it, ::onGenreFilterClick1).id(it.filterDisplayName).addTo(this)
        }

        HeaderEpoxyModel("Imdb Rate").id("filter_base_on_Imdb_rate").addTo(this)

        data2.forEach {
            FilterImdbEpoxyModel(it, ::onImdbFilterClick1).id(it.filterDisplayName).addTo(this)
        }

    }



//    private fun onGenreFilterClick(selectedFilter : String){
//        viewModel.viewModelScope.launch {
//            viewModel.store.update {currentState->
//                val currentSelectedFilter = currentState.movieFilterByGenre.selectedGenres
//                return@update currentState.copy(
//                    movieFilterByGenre = currentState.movieFilterByGenre.copy(
//                        selectedGenres =
//                        if(currentSelectedFilter.contains(selectedFilter)){
//                            currentSelectedFilter.filter { it != selectedFilter }.toSet()
//                        }else{
//                            currentSelectedFilter + setOf(selectedFilter)
//                        })
//                )
//            }
//        }
//    }


    private fun onGenreFilterClick1(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByGenreInfo1LiveData.value!!

                val newFilter =  currentSelectedFilter.copy(
                    selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
                        currentSelectedFilter.selectedGenres.filter { it != selectedFilter }.toSet()
                    }else{
                        currentSelectedFilter.selectedGenres + setOf(selectedFilter)
                    }
                )

                viewModel._filterByGenreInfo1LiveData.value = newFilter

        }
    }






//    private fun onImdbFilterClick(selectedFilter : Double){
//        viewModel.viewModelScope.launch {
//            viewModel.store.update {currentState->
//                val currentSelectedFilter = currentState.movieFilterByImdb.selectedImdbRate
//                return@update currentState.copy(
//                    movieFilterByImdb = currentState.movieFilterByImdb.copy(
//                        selectedImdbRate =
//                        if(currentSelectedFilter.contains(selectedFilter)){
//                            currentSelectedFilter.filter { it != selectedFilter }.toSet()
//                        }else{
//                            currentSelectedFilter + setOf(selectedFilter)
//                        })
//                )
//            }
//        }
//    }


    private fun onImdbFilterClick1(selectedFilter : Double){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByImdbRateInfo1LiveData.value!!

            val newFilter =  currentSelectedFilter.copy(
                selectedImdbRate = if(currentSelectedFilter.selectedImdbRate.contains(selectedFilter)){
                    currentSelectedFilter.selectedImdbRate.filter { it != selectedFilter }.toSet()
                }else{
                    currentSelectedFilter.selectedImdbRate + setOf(selectedFilter)
                }
            )

            viewModel._filterByImdbRateInfo1LiveData.value = newFilter
        }
    }



}

