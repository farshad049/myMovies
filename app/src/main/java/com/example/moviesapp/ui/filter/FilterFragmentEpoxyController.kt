package com.example.moviesapp.ui.filter

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.epoxy.FilterEpoxyModel
import com.example.moviesapp.model.domaind.UiFilter
import com.example.moviesapp.util.Constants
import kotlinx.coroutines.launch

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel
):TypedEpoxyController<List<UiFilter>>() {

    override fun buildModels(data: List<UiFilter>) {
        data.forEach {
            FilterEpoxyModel(it, ::onFilterClick).id(it.filterDisplayName).addTo(this)
        }
    }



    private fun onFilterClick(selectedFilter:String){
        viewModel.viewModelScope.launch {
            viewModel.store.update {currentState->
                val currentSelectedFilter = currentState.productFilterInfo.selectedGenres
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
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






}

