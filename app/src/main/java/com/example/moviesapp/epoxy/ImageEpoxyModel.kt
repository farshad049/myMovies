package com.example.moviesapp.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageItemBinding

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
