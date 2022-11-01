package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelEmptyFavoriteListBinding

class EmptyFavoriteMovieListEpoxyModel
    : ViewBindingKotlinModel<ModelEmptyFavoriteListBinding>(R.layout.model_empty_favorite_list) {
    override fun ModelEmptyFavoriteListBinding.bind() {

    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}