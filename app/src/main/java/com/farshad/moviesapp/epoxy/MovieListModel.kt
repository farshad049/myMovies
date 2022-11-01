package com.farshad.moviesapp.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelMovieListItemBinding
import com.farshad.moviesapp.model.domain.DomainMovieModel

data class MovieListModel(
    val item: DomainMovieModel,
    val click: (Int) -> Unit
) : ViewBindingKotlinModel<ModelMovieListItemBinding>(R.layout.model_movie_list_item){
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
        tvGenres.text=convertListToText(item.genres)

        root.setOnClickListener{click(item.id)}
    }




    private fun convertListToText(list : List<String>): String{
        val sb = StringBuilder()
        for (element in list) {
            sb.append(element).append(", ")
        }
        return sb.toString().substring(0, sb.length-2)
    }
}
