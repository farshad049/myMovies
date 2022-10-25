package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelSearchHistoryBinding
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId


data class SearchHistoryEpoxyModel(
    val movie : SearchHistoryEntityWithoutId,
    val onTitleClick : (Int) -> Unit,
    val onCloseClick : (Int) -> Unit
):ViewBindingKotlinModel<ModelSearchHistoryBinding>(R.layout.model_search_history) {
    override fun ModelSearchHistoryBinding.bind() {
        tvMovieTitle.text = movie.movieTitle
        tvMovieTitle.setOnClickListener { onTitleClick(movie.movieId) }
        ivClose.setOnClickListener { onCloseClick(movie.movieId) }
    }
}