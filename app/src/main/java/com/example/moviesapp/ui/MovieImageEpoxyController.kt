package com.example.moviesapp.ui

import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageViewBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class MovieImageEpoxyController: EpoxyController() {

    var isLoading:Boolean=true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var items: DomainMovieModel? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }



        val list=items!!.images.map {
            ImageEpoxyModel(it).id("it")
        }
        CarouselModel_()
            .id("image_carousel")
            .models(list)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)


    }










    data class ImageEpoxyModel(val imageUrl:String)
        : ViewBindingKotlinModel<ModelImageViewBinding>(R.layout.model_image_view){
        override fun ModelImageViewBinding.bind(){
            imageView.load(imageUrl)
        }
    }

    data class ThumbnailEpoxyModel(val imageUrl:String)
        : ViewBindingKotlinModel<ModelImageViewBinding>(R.layout.model_image_view){
        override fun ModelImageViewBinding.bind(){
            imageView.load(imageUrl)
        }
    }


}