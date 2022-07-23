package com.example.moviesapp.ui.movieDetail

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageItemBinding

import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class MovieImageEpoxyController: TypedEpoxyController<DomainMovieModel>() {

    override fun buildModels(data: DomainMovieModel?) {

        val list1=data!!.images.map {
            ImageEpoxyModel(it).id("it")
        }
        CarouselModel_()
            .id("image_carousel")
            .models(list1)
            .numViewsToShowOnScreen(1.25f)
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