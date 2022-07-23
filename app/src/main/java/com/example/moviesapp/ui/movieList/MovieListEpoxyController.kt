package com.example.moviesapp.ui.movieList

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelMovieListItemBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class MovieListEpoxyController(
    private val movieOnClick:(Int)-> Unit
): PagingDataEpoxyController<DomainMovieModel>() {

    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MoviesEpoxyModel(item!!,movieOnClick).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading1").addTo(this)
            return
        }
        super.addModels(models)
    }




    data class MoviesEpoxyModel(val item:DomainMovieModel,val click:(Int)->Unit)
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
}