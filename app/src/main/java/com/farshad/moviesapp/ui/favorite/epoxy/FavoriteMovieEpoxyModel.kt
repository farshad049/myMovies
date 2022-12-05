package com.farshad.moviesapp.ui.favorite.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelFavoriteMovieBinding
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

data class FavoriteMovieEpoxyModel(
    val movie : FavoriteMovieEntity,
    val onClick : (Int) -> Unit
): ViewBindingKotlinModel<ModelFavoriteMovieBinding>(R.layout.model_favorite_movie) {
    override fun ModelFavoriteMovieBinding.bind() {
        tvMovieTitle.text = movie.title
        root.setOnClickListener { onClick(movie.id) }
    }
}