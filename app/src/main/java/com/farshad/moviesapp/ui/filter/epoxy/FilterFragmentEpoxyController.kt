package com.farshad.moviesapp.ui.filter.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.ui.filter.model.DataForFilterFragmentEpoxy
import com.farshad.moviesapp.util.Constants.GENRE
import com.farshad.moviesapp.util.Constants.IMDBRATE
import java.util.*

class FilterFragmentEpoxyController(
    //private val viewModel: FilterViewModel,
    private val onClicks: FilterOnClicks
): TypedEpoxyController<DataForFilterFragmentEpoxy>() {

    override fun buildModels(data: DataForFilterFragmentEpoxy) {


        HeaderExpandableEpoxyModel(
            selectedExpandTitle = GENRE,
            onClick = {selectedExpandTitle-> onClicks.onExpandableHeaderClick(selectedExpandTitle)} ,
            isExpand = data.filteredByGenreList.isExpand
        ).id(UUID.randomUUID().toString()).addTo(this)


            if (data.filteredByGenreList.isExpand){
                data.filteredByGenreList.filterList.forEach {
                    FilterEpoxyModel(
                        uiFilter = it,
                        onFilterClick = {selectedFilter-> onClicks.onGenreFilterClick(selectedFilter)}
                    ).id(it.filterDisplayName).addTo(this)
                }
            }



        HeaderExpandableEpoxyModel(
            selectedExpandTitle = IMDBRATE,
            onClick = {selectedExpandTitle-> onClicks.onExpandableHeaderClick(selectedExpandTitle)} ,
            isExpand = data.filteredByImdbList.isExpand
        ).id(UUID.randomUUID().toString()).addTo(this)


        if (data.filteredByImdbList.isExpand){
            data.filteredByImdbList.filterList.forEach {
                FilterEpoxyModel(
                    uiFilter = it,
                    onFilterClick = {selectedImdbRate -> onClicks.onImdbFilterClick(selectedImdbRate)}
                ).id(it.filterDisplayName).addTo(this)
            }
        }




    }


//    private fun onExpandableHeaderClick(selectedExpandTitle : String){
//        viewModel.viewModelScope.launch {
//            val currentExpandIds = viewModel.expandItemsMutableFlow.value
//
//                val newExpandIds = currentExpandIds.copy(
//                    setOfExpandIds = if (currentExpandIds.setOfExpandIds.contains(selectedExpandTitle)){
//                        currentExpandIds.setOfExpandIds - selectedExpandTitle
//                    }else{
//                        currentExpandIds.setOfExpandIds + selectedExpandTitle
//                    }
//                )
//            viewModel._expandItemsMutableFlow.emit(newExpandIds)
//        }
//    }
//
//
//
//    private fun onGenreFilterClick(selectedFilter : String){
//        viewModel.viewModelScope.launch {
//            viewModel.filterByGenreInfoFlow.value.also { currentSelectedFilter->
//
//                val newFilter =  currentSelectedFilter.copy(
//                    selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
//                        currentSelectedFilter.selectedGenres - selectedFilter
//                    }else{
//                        currentSelectedFilter.selectedGenres + selectedFilter
//                    }
//                )
//
//                viewModel._filterByGenreInfoMutableFlow.emit(newFilter)
//            }
//
//
//        }
//    }
//
//
//
//    private fun onImdbFilterClick(selectedFilter : String){
//        viewModel.viewModelScope.launch {
//            val currentSelectedFilter = viewModel.filterByImdbRateInfoFlow.value
//
//            val newFilter =  currentSelectedFilter.copy(
//                selectedImdbRate = if(currentSelectedFilter.selectedImdbRate.contains(selectedFilter)){
//                    currentSelectedFilter.selectedImdbRate - selectedFilter
//                }else{
//                    currentSelectedFilter.selectedImdbRate + selectedFilter
//                }
//            )
//
//            viewModel._filterByImdbRateInfoFlow.emit(newFilter)
//        }
//    }



}

