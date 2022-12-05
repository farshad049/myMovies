package com.farshad.moviesapp.ui.dashboard.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelDashboardShimmerBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

 class ModelShimmerDashboard
     : ViewBindingKotlinModel<ModelDashboardShimmerBinding>(R.layout.model_dashboard_shimmer){
    override fun ModelDashboardShimmerBinding.bind() {
        shimmerLayout.startShimmer()
    }

}
