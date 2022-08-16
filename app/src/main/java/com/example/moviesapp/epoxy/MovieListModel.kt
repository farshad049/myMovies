package com.example.moviesapp.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelMovieListItemBinding
import com.example.moviesapp.model.domain.DomainMovieModel

data class MovieListModel(val item: DomainMovieModel, val click:(Int)->Unit)
    : ViewBindingKotlinModel<ModelMovieListItemBinding>(R.layout.model_movie_list_item){
    override fun ModelMovieListItemBinding.bind() {
        progressImage.isVisible=true
        ivMovieCard.load(item.poster){
            listener { request, result ->
                progressImage.isGone=true
            }
        }
        tvTitle.text=item.title
        tvCountry.text=item.country
        tvIMDB.text=item.imdb_rating
        tvYear.text=item.year
        tvGenres.text=item.genres.toString()

        root.setOnClickListener{click(item.id)}
    }
}
