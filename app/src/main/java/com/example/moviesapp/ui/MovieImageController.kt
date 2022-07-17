package com.example.moviesapp.ui

import coil.load
import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageViewBinding
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.network.NetworkMovieModel

class MovieImageController: TypedEpoxyController<NetworkMovieModel>() {
    override fun buildModels(data: NetworkMovieModel?) {

        if (data == null){

            return
        }else{
            data.images.forEach {
                ImageEpoxyModel(it).id("images_$it.id").addTo(this)
            }
        }

    }

    data class ImageEpoxyModel(val imageUrl:String)
        : ViewBindingKotlinModel<ModelImageViewBinding>(R.layout.model_image_view){
        override fun ModelImageViewBinding.bind(){
            imageView.load(imageUrl)
        }
    }
}