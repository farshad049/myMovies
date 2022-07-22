package com.example.moviesapp.ui.movieDetail

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageCaruselBinding
import com.example.moviesapp.databinding.ModelSimilarMovieItemBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class MovieImageEpoxyController: Typed2EpoxyController<DomainMovieModel,List<DomainMovieModel>>() {

    override fun buildModels(data1: DomainMovieModel?, data2: List<DomainMovieModel>?) {
        val list1=data1!!.images.map {
            ImageEpoxyModel(it).id("it")
        }
        CarouselModel_()
            .id("image_carousel")
            .models(list1)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)

        if (data2.isNullOrEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        val list2=data2!!.map {
            SimilarGenreEpoxyModel(it).id("it")
        }
        CarouselModel_()
            .id("similar_genre_carasel")
            .models(list2)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)

    }











    data class ImageEpoxyModel(val imageUrl:String)
        : ViewBindingKotlinModel<ModelImageCaruselBinding>(R.layout.model_image_carusel){
        override fun ModelImageCaruselBinding.bind(){
            progressImage.isVisible=true
            imageView.load(imageUrl){
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
        }
    }

    data class SimilarGenreEpoxyModel(val item:DomainMovieModel)
        : ViewBindingKotlinModel<ModelSimilarMovieItemBinding>(R.layout.model_similar_movie_item){
        override fun ModelSimilarMovieItemBinding.bind(){
            ivMovie.load(item.poster)
        }
    }




}