package com.farshad.moviesapp.ui.movieListByGenre

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.farshad.moviesapp.epoxy.HeaderEpoxyModel
import com.farshad.moviesapp.epoxy.LoadingEpoxyModel
import com.farshad.moviesapp.epoxy.MovieListModel
import com.farshad.moviesapp.model.domain.DomainMovieModel

class MovieListByGenreController (
    private val onMovieClick:(Int)-> Unit,
    private val genreName:String
): PagingDataEpoxyController<DomainMovieModel>() {


    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModel(item!!,onMovieClick)
            .id("genre${item.id}")
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading8").addTo(this)
            return
        }

        HeaderEpoxyModel(genreName).id("genreName").addTo(this)

        super.addModels(models)
    }


}