package com.farshad.moviesapp.ui.favorite.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.epoxy.HeaderEpoxyModel
import java.util.*

class FavoriteEpoxyController(
    private val onClick: (Int)->Unit
) : TypedEpoxyController<List<FavoriteMovieEntity>>() {
    override fun buildModels(data: List<FavoriteMovieEntity>) {
        if (data.isEmpty()){
            EmptyFavoriteMovieListEpoxyModel().id(UUID.randomUUID().toString()).addTo(this)
            return
        }


            HeaderEpoxyModel("Favorite Movies").id(UUID.randomUUID().toString()).addTo(this)
            data.forEach {
                FavoriteMovieEpoxyModel(it , onClick).id(it.id).addTo(this)
            }

    }
}