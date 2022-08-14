package com.example.moviesapp.ui.dashboard


import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelSimilarMovieItemBinding
import com.example.moviesapp.databinding.ModelTitleBinding
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.MovieThumbnailModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.ui.movieDetail.MovieDetailEpoxyController

class DashboardEpoxyController(private val onMovieClick:(Int)->Unit)
    : TypedEpoxyController<List<DomainMovieModel?>>() {


    override fun buildModels(data: List<DomainMovieModel?>?) {

        if (data.isNullOrEmpty() ){
            LoadingEpoxyModel().id("loading6").addTo(this)
            return
        }

        HeaderEpoxyModel("Top Rated Movies").id("top_rated_movies").addTo(this)


        val topRatedMovieList= data.map {
            MovieThumbnailModel(it,onMovieClick).id(it?.id)
        }
        CarouselModel_()
            .id("top_rated_carousel")
            .models(topRatedMovieList)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)








    }








}