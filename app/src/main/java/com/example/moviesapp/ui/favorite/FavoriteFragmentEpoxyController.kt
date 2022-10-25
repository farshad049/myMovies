package com.example.moviesapp.ui.favorite

import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.epoxy.EmptyFavoriteMovieListEpoxyModel
import com.example.moviesapp.epoxy.FavoriteMovieEpoxyModel
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.roomDatabase.Entity.FavoriteMovieEntity

class FavoriteFragmentEpoxyController(
    private val onMovieClick : (Int) -> Unit
):TypedEpoxyController<List<FavoriteMovieEntity>>() {

    override fun buildModels(data: List<FavoriteMovieEntity>?) {
        if (data.isNullOrEmpty()){
            EmptyFavoriteMovieListEpoxyModel().id("empty").addTo(this)
            return
        }

        HeaderEpoxyModel("Favorite Movies").id("favorite_movies").addTo(this)

        data.forEach {
            FavoriteMovieEpoxyModel(it , onMovieClick).id(it.id).addTo(this)
        }


    }
}