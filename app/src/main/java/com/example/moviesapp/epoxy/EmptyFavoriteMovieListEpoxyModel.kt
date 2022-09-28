package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelEmptyFavoriteListBinding

class EmptyFavoriteMovieListEpoxyModel
    : ViewBindingKotlinModel<ModelEmptyFavoriteListBinding>(R.layout.model_empty_favorite_list) {
    override fun ModelEmptyFavoriteListBinding.bind() {

    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}