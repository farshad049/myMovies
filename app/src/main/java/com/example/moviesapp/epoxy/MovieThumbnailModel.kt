package com.example.moviesapp.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelMovieThumbnailBinding

import com.example.moviesapp.model.domain.DomainMovieModel

data class MovieThumbnailModel(val item: DomainMovieModel?, val onclick:(Int)->Unit)
    : ViewBindingKotlinModel<ModelMovieThumbnailBinding>(R.layout.model_movie_thumbnail){
    override fun ModelMovieThumbnailBinding.bind(){
        progressImage.isVisible=true
        ivMovie.load(item!!.poster){
            listener { request, result ->
                progressImage.isGone=true
            }
        }
        root.setOnClickListener { onclick(item.id) }
    }
}