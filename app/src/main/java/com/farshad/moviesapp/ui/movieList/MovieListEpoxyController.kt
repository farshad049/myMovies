package com.farshad.moviesapp.ui.movieList

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.farshad.moviesapp.epoxy.LoadingEpoxyModel
import com.farshad.moviesapp.epoxy.MovieListModel
import com.farshad.moviesapp.model.domain.DomainMovieModel

class MovieListEpoxyController(
    private val movieOnClick:(Int)-> Unit
): PagingDataEpoxyController<DomainMovieModel>() {

    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModel(item!!,movieOnClick).id(item.id)
    }


    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading1").addTo(this)
            return
        }
        super.addModels(models)
    }





}