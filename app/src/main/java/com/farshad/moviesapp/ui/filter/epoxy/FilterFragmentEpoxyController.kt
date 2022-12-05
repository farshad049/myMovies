package com.farshad.moviesapp.ui.filter.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.ui.filter.FilterViewModel
import com.farshad.moviesapp.ui.filter.model.DataForFilterFragmentEpoxy
import com.farshad.moviesapp.util.Constants.GENRE
import com.farshad.moviesapp.util.Constants.IMDBRATE
import kotlinx.coroutines.launch
import java.util.*

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel,
): TypedEpoxyController<DataForFilterFragmentEpoxy>() {

    override fun buildModels(data: DataForFilterFragmentEpoxy) {


        HeaderExpandableEpoxyModel(GENRE, ::onExpandableHeaderClick , data.filteredByGenreList.isExpand).id(
            UUID.randomUUID().toString()).addTo(this)


            if (data.filteredByGenreList.isExpand){
                data.filteredByGenreList.filterList.forEach {
                    FilterEpoxyModel(it, ::onGenreFilterClick  ).id(it.filterDisplayName).addTo(this)
                }
            }



        HeaderExpandableEpoxyModel(IMDBRATE, ::onExpandableHeaderClick , data.filteredByImdbList.isExpand).id(UUID.randomUUID().toString()).addTo(this)


        if (data.filteredByImdbList.isExpand){
            data.filteredByImdbList.filterList.forEach {
                FilterEpoxyModel(it, ::onImdbFilterClick).id(it.filterDisplayName).addTo(this)
            }
        }




    }


    private fun onExpandableHeaderClick(selectedExpandTitle : String){
        viewModel.viewModelScope.launch {
            val currentExpandIds = viewModel.expandItemsMutableLiveData.value

                val newExpandIds = currentExpandIds.copy(
                    setOfExpandIds = if (currentExpandIds.setOfExpandIds.contains(selectedExpandTitle)){
                        currentExpandIds.setOfExpandIds - selectedExpandTitle
                    }else{
                        currentExpandIds.setOfExpandIds + selectedExpandTitle
                    }
                )
            viewModel._expandItemsMutableLiveData.value = newExpandIds
        }
    }



    private fun onGenreFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            viewModel.filterByGenreInfoLiveData.value.also {currentSelectedFilter->

                val newFilter =  currentSelectedFilter.copy(
                    selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
                        currentSelectedFilter.selectedGenres - selectedFilter
                    }else{
                        currentSelectedFilter.selectedGenres + selectedFilter
                    }
                )

                viewModel._filterByGenreInfoLiveData.value = newFilter
            }


        }
    }



    private fun onImdbFilterClick(selectedFilter : String){
        viewModel.viewModelScope.launch {
            val currentSelectedFilter = viewModel.filterByImdbRateInfoLiveData.value

            val newFilter =  currentSelectedFilter.copy(
                selectedImdbRate = if(currentSelectedFilter.selectedImdbRate.contains(selectedFilter)){
                    currentSelectedFilter.selectedImdbRate - selectedFilter
                }else{
                    currentSelectedFilter.selectedImdbRate + selectedFilter
                }
            )

            viewModel._filterByImdbRateInfo1LiveData.value = newFilter
        }
    }



}

