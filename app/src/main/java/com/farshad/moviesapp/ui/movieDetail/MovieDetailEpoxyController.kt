package com.farshad.moviesapp.ui.movieDetail

import android.content.Context
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.ui.UiMovieDetailModel
import com.farshad.moviesapp.epoxy.*
import com.farshad.moviesapp.ui.favorite.FavoriteFragmentViewModel

class MovieDetailEpoxyController(
    private val context: Context,
    private val favoriteFragmentViewModel: FavoriteFragmentViewModel,
    private val onSimilarMovieClick:(Int)->Unit
): TypedEpoxyController<UiMovieDetailModel>() {

    override fun buildModels(data:UiMovieDetailModel) {

        if (data.movie == null) {
            LoadingEpoxyModel().id("loadingMovieDetail").addTo(this)
            return
        }

        MovieDetailUpEpoxyModel(
            context  ,
            data.movie ,
            favoriteFragmentViewModel,
            data.isFavorite
        ).id(data.movie.id).addTo(this)


        if (data.movie.images.isNotEmpty()){
            val list1=data.movie.images.map {
                ImageEpoxyModel(it).id(it)
            }

            CarouselModel_()
                .id("image_carousel")
                .models(list1)
                .numViewsToShowOnScreen(1.25f)
                .addTo(this)
        }



        if (data.similarMovies.isNotEmpty()){
            HeaderEpoxyModel("Similar Movies").id("similar_movies").addTo(this)

            val list2= data.similarMovies.map {
                MovieThumbnailModel(it, onSimilarMovieClick).id(it.id)
            }
            CarouselModel_()
                .id("similar_genre_carousel")
                .models(list2)
                .numViewsToShowOnScreen(2.7f)
                .addTo(this)
        }


    }















}