package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelMovieListShimmerBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

class ModelMovieListShimmer
    : ViewBindingKotlinModel<ModelMovieListShimmerBinding>(R.layout.model_movie_list_shimmer){
    override fun ModelMovieListShimmerBinding.bind() {
        shimmerLayout.startShimmer()
    }
}