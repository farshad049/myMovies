package com.farshad.moviesapp.ui.filter

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.epoxy.FilterEpoxyModel
import com.farshad.moviesapp.epoxy.HeaderExpandableEpoxyModel
import com.farshad.moviesapp.data.model.ui.DataForFilterFragmentEpoxy
import com.farshad.moviesapp.util.Constants.GENRE
import com.farshad.moviesapp.util.Constants.IMDBRATE
import kotlinx.coroutines.launch

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel,
): TypedEpoxyController<DataForFilterFragmentEpoxy>() {

    override fun buildModels(data: DataForFilterFragmentEpoxy) {


        HeaderExpandableEpoxyModel(GENRE, ::onExpandableHeaderClick , data.filteredByGenreList.isExpand).id("filter_base_on_genres").addTo(this)


            if (data.filteredByGenreList.isExpand){
                data.filteredByGenreList.filterList.forEach {
                    FilterEpoxyModel(it, ::onGenreFilterClick  ).id(it.filterDisplayName).addTo(this)
                }
            }



        HeaderExpandableEpoxyModel(IMDBRATE, ::onExpandableHeaderClick , data.filteredByImdbList.isExpand).id("filter_base_on_Imdb_rate").addTo(this)


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
                        currentExpandIds.setOfExpandIds.filter { it != selectedExpandTitle }.toSet()
                    }else{
                        currentExpandIds.setOfExpandIds + setOf(selectedExpandTitle)
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
                        currentSelectedFilter.selectedGenres.filter { it != selectedFilter }.toSet()
                    }else{
                        currentSelectedFilter.selectedGenres + setOf(selectedFilter)
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
                    currentSelectedFilter.selectedImdbRate.filter { it != selectedFilter }.toSet()
                }else{
                    currentSelectedFilter.selectedImdbRate + setOf(selectedFilter)
                }
            )

            viewModel._filterByImdbRateInfo1LiveData.value = newFilter
        }
    }



}

