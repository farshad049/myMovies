package com.example.moviesapp.ui.movieDetail

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelSimilarMovieItemBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class MovieSimilarEpoxyController(private val onSimilarMovieClick:(Int)->Unit)
    :TypedEpoxyController<List<DomainMovieModel?>>() {

    override fun buildModels(data: List<DomainMovieModel?>) {

        if (data.isNullOrEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        val list2=data!!.map {
            SimilarGenreEpoxyModel(it,onSimilarMovieClick).id(it?.id)
        }
        CarouselModel_()
            .id("similar_genre_carousel")
            .models(list2)
            .numViewsToShowOnScreen(2.7f)
            .addTo(this)

    }





    data class SimilarGenreEpoxyModel(val item:DomainMovieModel?,val onclick:(Int)->Unit)
        : ViewBindingKotlinModel<ModelSimilarMovieItemBinding>(R.layout.model_similar_movie_item){
        override fun ModelSimilarMovieItemBinding.bind(){
            progressImage.isVisible=true
            ivMovie.load(item!!.poster){
                listener { request, result ->
                    progressImage.isGone=true
                }
            }

            root.setOnClickListener { onclick(item!!.id) }
        }
    }

}