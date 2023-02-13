package com.farshad.moviesapp.ui.movieList.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.ui.filter.FilterViewModel
import kotlinx.coroutines.launch
import java.util.*

class FilterCarouselEpoxyController(
    private val viewModel: FilterViewModel
    ) :TypedEpoxyController<Set<String>>() {

    override fun buildModels(data: Set<String>?) {

        if (data != null){
            val selectedGenreList = data.map { CarouselFilterEpoxyModel(it , ::onFilterClick).id(it) }
            CarouselModel_()
                .id(UUID.randomUUID().toString())
                .models(selectedGenreList)
                .addTo(this)
        }



    }






    private fun onFilterClick(selectedFilter : String){
        when{

            viewModel.filterByGenreInfoFlow.value.genres.contains(selectedFilter) -> {
                viewModel.viewModelScope.launch {
                    val currentSelectedFilter = viewModel.filterByGenreInfoFlow.value

                    val newFilter =  currentSelectedFilter.copy(
                        selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
                            currentSelectedFilter.selectedGenres - selectedFilter
                        }else{
                            currentSelectedFilter.selectedGenres
                        }
                    )
                    viewModel._filterByGenreInfoMutableFlow.emit(newFilter)
                }
            }


            viewModel.filterByImdbRateInfoFlow.value.imdbRate.contains(selectedFilter) -> {
                viewModel.viewModelScope.launch {
                    val currentSelectedFilter = viewModel.filterByImdbRateInfoFlow.value

                    val newFilter =  currentSelectedFilter.copy(
                        selectedImdbRate = if(currentSelectedFilter.selectedImdbRate.contains(selectedFilter)){
                            currentSelectedFilter.selectedImdbRate - selectedFilter
                        }else{
                            currentSelectedFilter.selectedImdbRate
                        }
                    )
                    viewModel._filterByImdbRateInfoFlow.emit(newFilter)
                }
            }



        }

    }




}