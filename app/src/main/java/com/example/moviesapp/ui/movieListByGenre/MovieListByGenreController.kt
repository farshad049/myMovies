package com.example.moviesapp.ui.movieListByGenre

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.MovieListModel
import com.example.moviesapp.model.domain.DomainMovieModel

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
            LoadingEpoxyModel().id("loading5").addTo(this)
            return
        }

        HeaderEpoxyModel(genreName).id("genreName").addTo(this)
        super.addModels(models)
    }


}