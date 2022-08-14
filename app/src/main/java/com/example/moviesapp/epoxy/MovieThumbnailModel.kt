package com.example.moviesapp.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelSimilarMovieItemBinding
import com.example.moviesapp.model.domain.DomainMovieModel

data class MovieThumbnailModel(val item: DomainMovieModel?, val onclick:(Int)->Unit)
    : ViewBindingKotlinModel<ModelSimilarMovieItemBinding>(R.layout.model_similar_movie_item){
    override fun ModelSimilarMovieItemBinding.bind(){
        progressImage.isVisible=true
        ivMovie.load(item!!.poster){
            listener { request, result ->
                progressImage.isGone=true
            }
        }
        root.setOnClickListener { onclick(item.id) }
    }
}