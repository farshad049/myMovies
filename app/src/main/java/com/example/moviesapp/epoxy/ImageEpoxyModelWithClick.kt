package com.example.moviesapp.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.commit451.coiltransformations.BlurTransformation
import com.example.moviesapp.MoviesApplication.Companion.context
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelImageItemBinding

data class ImageEpoxyModelWithClick(val imageUrl:String,val id:Int,val onclick:(Int)->Unit)
    : ViewBindingKotlinModel<ModelImageItemBinding>(R.layout.model_image_item){
    override fun ModelImageItemBinding.bind(){
        progressImage.isVisible=true
        imageView.load(imageUrl){
            listener { request, result ->
                progressImage.isGone=true
            }

        }
        root.setOnClickListener { onclick(id) }
    }
}
