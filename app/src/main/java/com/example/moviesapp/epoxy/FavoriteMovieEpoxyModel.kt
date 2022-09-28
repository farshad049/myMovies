package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelFavoriteMovieBinding
import com.example.moviesapp.roomDatabase.MovieEntity

class FavoriteMovieEpoxyModel(
    val movie : MovieEntity,
    val onClick : (Int) -> Unit
):ViewBindingKotlinModel<ModelFavoriteMovieBinding>(R.layout.model_favorite_movie) {
    override fun ModelFavoriteMovieBinding.bind() {
        tvMovieTitle.text = movie.title
        root.setOnClickListener { onClick(movie.id) }

    }
}