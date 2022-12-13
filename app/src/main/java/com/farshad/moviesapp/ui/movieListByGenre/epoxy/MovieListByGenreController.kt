package com.farshad.moviesapp.ui.movieListByGenre.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.farshad.moviesapp.epoxy.HeaderEpoxyModel
import com.farshad.moviesapp.epoxy.LoadingEpoxyModel
import com.farshad.moviesapp.ui.movieList.epoxy.MovieListModel
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.epoxy.ModelMovieListShimmer
import java.util.*

class MovieListByGenreController (
    private val onMovieClick:(Int)-> Unit,
    private val genreName:String
): PagingDataEpoxyController<DomainMovieModel>() {


    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModel(item!!,onMovieClick).id(UUID.randomUUID().toString())
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        if (models.isEmpty()){
            repeat(7){ ModelMovieListShimmer().id(UUID.randomUUID().toString()).addTo(this)}
            return
        }

        HeaderEpoxyModel(genreName).id(UUID.randomUUID().toString()).addTo(this)

        super.addModels(models)
    }


}