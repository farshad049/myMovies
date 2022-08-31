package com.example.moviesapp.ui.filter

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.Typed2EpoxyController
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.epoxy.FilterEpoxyModel
import com.example.moviesapp.epoxy.FilterImdbEpoxyModel
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.model.ui.UiGenreFilter
import com.example.moviesapp.model.ui.UiImdbRateFilter
import kotlinx.coroutines.launch

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel
):Typed2EpoxyController<List<UiGenreFilter>,List<UiImdbRateFilter>>() {

    override fun buildModels(data1: List<UiGenreFilter>, data2: List<UiImdbRateFilter>) {

        HeaderEpoxyModel("Genres").id("filter_base_on_genres").addTo(this)

        data1.forEach {
            FilterEpoxyModel(it, ::onGenreFilterClick).id(it.filterDisplayName).addTo(this)
        }

        HeaderEpoxyModel("Imdb Rate").id("filter_base_on_Imdb_rate").addTo(this)

        data2.forEach {
            FilterImdbEpoxyModel(it, ::onImdbFilterClick).id(it.filterDisplayName).addTo(this)
        }

    }



    private fun onGenreFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            viewModel.store.update {currentState->
                val currentSelectedFilter = currentState.movieFilterByGenre.selectedGenres
                return@update currentState.copy(
                    movieFilterByGenre = currentState.movieFilterByGenre.copy(
                        selectedGenres =
                        if(currentSelectedFilter.contains(selectedFilter)){
                            currentSelectedFilter.filter { it != selectedFilter }.toSet()
                        }else{
                            currentSelectedFilter + setOf(selectedFilter)
                        })
                )
            }
        }
    }






    private fun onImdbFilterClick(selectedFilter : Double){
        viewModel.viewModelScope.launch {
            viewModel.store.update {currentState->
                val currentSelectedFilter = currentState.movieFilterByImdb.selectedImdbRate
                return@update currentState.copy(
                    movieFilterByImdb = currentState.movieFilterByImdb.copy(
                        selectedImdbRate =
                        if(currentSelectedFilter.contains(selectedFilter)){
                            currentSelectedFilter.filter { it != selectedFilter }.toSet()
                        }else{
                            currentSelectedFilter + setOf(selectedFilter)
                        })
                )
            }
        }
    }



}

