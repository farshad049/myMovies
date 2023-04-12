package com.farshad.moviesapp.ui.movieDetail.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelImageRectangleBinding

import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

data class ImageEpoxyModel(val imageUrl:String)
    : ViewBindingKotlinModel<ModelImageRectangleBinding>(R.layout.model_image_rectangle){
    override fun ModelImageRectangleBinding.bind(){
        progressImage.isVisible=true
        imageView.load(imageUrl){
            listener { request, result ->
                progressImage.isGone=true
            }
        }
    }
}
