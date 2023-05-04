package com.farshad.moviesapp.ui.favorite.epoxy

import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.databinding.ModelFavoriteFooterBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelFavoriteHeaderBinding
import com.farshad.moviesapp.util.Convertors

data class FavoriteFooterModel(
    val movie: DomainMovieModel
):ViewBindingKotlinModel<ModelFavoriteFooterBinding>(R.layout.model_favorite_footer){

    override fun ModelFavoriteFooterBinding.bind() {
        tvTITLE.text= movie.title
        tvIMDB.text= movie.imdb_rating
        tvYEAR.text= movie.year
        tvRATE.text= movie.rated
        tvGENRE.text= Convertors().convertListToText(movie.genres)
        tvDIRECTOR.text= movie.director
    }

}
