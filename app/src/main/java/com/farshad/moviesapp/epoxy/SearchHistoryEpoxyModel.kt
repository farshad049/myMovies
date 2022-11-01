package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelSearchHistoryBinding
import com.farshad.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId


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