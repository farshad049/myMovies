package com.example.moviesapp.ui.filter

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.epoxy.FilterEpoxyModel
import com.example.moviesapp.epoxy.HeaderExpandableEpoxyModel
import com.example.moviesapp.model.ui.FilterByGenreAndImdbRate
import com.example.moviesapp.util.Constants.GENRE
import com.example.moviesapp.util.Constants.IMDBRATE
import kotlinx.coroutines.launch

class FilterFragmentEpoxyController(
    private val viewModel: FilterViewModel ,
): TypedEpoxyController<FilterByGenreAndImdbRate>() {

    override fun buildModels(data: FilterByGenreAndImdbRate) {


        HeaderExpandableEpoxyModel(GENRE, ::onExpandableHeaderClick).id("filter_base_on_genres").addTo(this)


        data.filteredByGenreList.forEach {
            if (it.isExpand){
                FilterEpoxyModel(it, ::onGenreFilterClick  ).id(it.filterDisplayName).addTo(this)
            }
        }


        HeaderExpandableEpoxyModel(IMDBRATE, ::onExpandableHeaderClick).id("filter_base_on_Imdb_rate").addTo(this)


        data.filteredByImdbList.forEach {
            if (it.isExpand){
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
            val currentSelectedFilter = viewModel.filterByGenreInfoLiveData.value

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

