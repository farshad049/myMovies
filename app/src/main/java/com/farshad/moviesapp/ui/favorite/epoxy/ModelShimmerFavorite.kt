package com.farshad.moviesapp.ui.favorite.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelDashboardShimmerBinding
import com.farshad.moviesapp.databinding.ModelFavoriteShimmerBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

 class ModelShimmerFavorite
     : ViewBindingKotlinModel<ModelFavoriteShimmerBinding>(R.layout.model_favorite_shimmer){
    override fun ModelFavoriteShimmerBinding.bind() {
        shimmerLayout.startShimmer()
    }

}
