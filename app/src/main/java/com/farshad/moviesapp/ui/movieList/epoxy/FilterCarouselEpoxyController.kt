package com.farshad.moviesapp.ui.movieList.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.ui.filter.FilterViewModel
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


            viewModel.filterByGenreInfoLiveData.value.genres.contains(selectedFilter) -> {
                val currentSelectedFilter = viewModel.filterByGenreInfoLiveData.value
                val newFilter =  currentSelectedFilter.copy(
                    selectedGenres = if(currentSelectedFilter.selectedGenres.contains(selectedFilter)){
                        currentSelectedFilter.selectedGenres - selectedFilter
                    }else{
                        currentSelectedFilter.selectedGenres + selectedFilter
                    }
                )
                viewModel._filterByGenreInfoLiveData.value = newFilter
            }


            viewModel.filterByImdbRateInfoLiveData.value.imdbRate.contains(selectedFilter) -> {
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




}