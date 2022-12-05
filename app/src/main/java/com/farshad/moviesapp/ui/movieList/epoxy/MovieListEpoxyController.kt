package com.farshad.moviesapp.ui.movieList.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import java.util.*

class MovieListEpoxyController(
    private val movieOnClick:(Int)-> Unit
): PagingDataEpoxyController<DomainMovieModel>() {

    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModel(item!!,movieOnClick).id(item.id)
    }


    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()){
            repeat(7){ModelMovieListShimmer().id(UUID.randomUUID().toString()).addTo(this)}
            return
        }
        super.addModels(models)
    }





}