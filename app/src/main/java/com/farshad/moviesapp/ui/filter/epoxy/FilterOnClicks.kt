package com.farshad.moviesapp.ui.filter.epoxy

import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.ui.filter.FilterViewModel
import kotlinx.coroutines.launch

class FilterOnClicks(private val viewModel: FilterViewModel) {

    fun onExpandableHeaderClick(selectedExpandTitle : String){
        viewModel.viewModelScope.launch {
            val currentExpandIds = viewModel.expandItemsMutableFlow.value

            val newExpandIds = currentExpandIds.copy(
                setOfExpandIds = if (currentExpandIds.setOfExpandIds.contains(selectedExpandTitle)){
                    currentExpandIds.setOfExpandIds - selectedExpandTitle
                }else{
                    currentExpandIds.setOfExpandIds + selectedExpandTitle
                }
            )
            viewModel._expandItemsMutableFlow.emit(newExpandIds)
        }
    }


    fun onGenreFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            viewModel.filterByGenreInfoFlow.value.also { currentSelectedFilter->

                val newFilter =  currentSelectedFilter.copy(
                    selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
                        currentSelectedFilter.selectedGenres - selectedFilter
                    }else{
                        currentSelectedFilter.selectedGenres + selectedFilter
                    }
                )

                viewModel._filterByGenreInfoMutableFlow.emit(newFilter)
            }

        }
    }


    fun onImdbFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByImdbRateInfoFlow.value

            val newFilter =  currentSelectedFilter.copy(
                selectedImdbRate = if(currentSelectedFilter.selectedImdbRate.contains(selectedFilter)){
                    currentSelectedFilter.selectedImdbRate - selectedFilter
                }else{
                    currentSelectedFilter.selectedImdbRate + selectedFilter
                }
            )

            viewModel._filterByImdbRateInfoFlow.emit(newFilter)
        }
    }




}