package com.example.moviesapp.ui.filter

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.epoxy.CarouselFilterEpoxyModel
import com.example.moviesapp.epoxy.FilterEpoxyModel
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.model.ui.FilterByGenreAndImdbRate
import kotlinx.coroutines.launch

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel ,
): TypedEpoxyController<FilterByGenreAndImdbRate>() {

    override fun buildModels(data: FilterByGenreAndImdbRate) {

        HeaderEpoxyModel("Genres").id("filter_base_on_genres").addTo(this)


        data.filteredByGenreList.forEach {
            FilterEpoxyModel(it, ::onGenreFilterClick).id(it.filterDisplayName).addTo(this)
        }

        HeaderEpoxyModel("Imdb Rate").id("filter_base_on_Imdb_rate").addTo(this)

        data.filteredByImdbList.forEach {
            FilterEpoxyModel(it, ::onImdbFilterClick).id(it.filterDisplayName).addTo(this)
        }



    }



    private fun onGenreFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByGenreInfo1LiveData.value

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



    private fun onImdbFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByImdbRateInfo1LiveData.value

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

