package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelFavoriteMovieBinding
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity

class FavoriteMovieEpoxyModel(
    val movie : FavoriteMovieEntity,
    val onClick : (Int) -> Unit
):ViewBindingKotlinModel<ModelFavoriteMovieBinding>(R.layout.model_favorite_movie) {
    override fun ModelFavoriteMovieBinding.bind() {
        tvMovieTitle.text = movie.title
        root.setOnClickListener { onClick(movie.id) }
    }
}