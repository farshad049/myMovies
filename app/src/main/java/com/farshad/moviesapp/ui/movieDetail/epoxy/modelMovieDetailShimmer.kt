package com.farshad.moviesapp.ui.movieDetail.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelMovieDetailShimmerBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

class modelMovieDetailShimmer: ViewBindingKotlinModel<ModelMovieDetailShimmerBinding>(R.layout.model_movie_detail_shimmer){
    override fun ModelMovieDetailShimmerBinding.bind() {
        shimmerLayout.startShimmer()
    }

}