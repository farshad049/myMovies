package com.example.moviesapp.ui.movieDetail

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageItemBinding
import com.example.moviesapp.databinding.ModelSimilarMovieItemBinding
import com.example.moviesapp.databinding.ModelTitleBinding
import com.example.moviesapp.epoxy.HeaderEpoxyModel
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.MovieThumbnailModel

import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class MovieDetailEpoxyController(private val onSimilarMovieClick:(Int)->Unit)
    : Typed2EpoxyController<DomainMovieModel,List<DomainMovieModel?>>() {

    override fun buildModels(data1: DomainMovieModel?,data2: List<DomainMovieModel?>) {

        if (data1 == DomainMovieModel() || data2.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }




        val list1=data1!!.images.map {
            ImageEpoxyModel(it).id("it")
        }

        CarouselModel_()
            .id("image_carousel")
            .models(list1)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)



        HeaderEpoxyModel("Similar Movies").id("similar_movies").addTo(this)




        val list2= data2.map {
            MovieThumbnailModel(it, onSimilarMovieClick).id(it?.id)
        }
        CarouselModel_()
            .id("similar_genre_carousel")
            .models(list2)
            .numViewsToShowOnScreen(2.7f)
            .addTo(this)

    }











    data class ImageEpoxyModel(val imageUrl:String)
        : ViewBindingKotlinModel<ModelImageItemBinding>(R.layout.model_image_item){
        override fun ModelImageItemBinding.bind(){
            progressImage.isVisible=true
            imageView.load(imageUrl){
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
        }
    }



}