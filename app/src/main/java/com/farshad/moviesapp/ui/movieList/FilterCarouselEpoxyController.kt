package com.farshad.moviesapp.ui.movieList

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.farshad.moviesapp.epoxy.CarouselFilterEpoxyModel

class FilterCarouselEpoxyController(
    private val viewModel: FilterViewModel
    ) :TypedEpoxyController<Set<String>>() {

    override fun buildModels(data: Set<String>?) {

        if (data != null){
            val selectedGenreList = data.map { CarouselFilterEpoxyModel(it , ::onFilterClick).id(it) }
            CarouselModel_()
                .id("filter_carousel1")
                .models(selectedGenreList)
                .addTo(this)
        }



    }






    private fun onFilterClick(selectedFilter : String){
        when{


            viewModel.filterByGenreInfoLiveData.value.genres.contains(selectedFilter) -> {
                val currentSelectedFilter = viewModel.filterByGenreInfoLiveData.value
                val newFilter =  currentSelectedFilter.copy(
                    selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
                        currentSelectedFilter.selectedGenres.filter { it != selectedFilter }.toSet()
                    }else{
                        currentSelectedFilter.selectedGenres
                    }
                )
                viewModel._filterByGenreInfoLiveData.value = newFilter
            }


            viewModel.filterByImdbRateInfoLiveData.value.imdbRate.contains(selectedFilter) -> {
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




}