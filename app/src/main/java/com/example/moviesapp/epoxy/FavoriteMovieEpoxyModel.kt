package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelFavoriteMovieBinding
import com.example.moviesapp.roomDatabase.Entity.FavoriteMovieEntity

class FavoriteMovieEpoxyModel(
    val movie : FavoriteMovieEntity,
    val onClick : (Int) -> Unit
):ViewBindingKotlinModel<ModelFavoriteMovieBinding>(R.layout.model_favorite_movie) {
    override fun ModelFavoriteMovieBinding.bind() {
        tvMovieTitle.text = movie.title
        root.setOnClickListener { onClick(movie.id) }
    }
}