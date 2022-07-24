package com.example.moviesapp.ui.search

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.arch.SearchDataSource
import com.example.moviesapp.databinding.ModelExeptionErrorBinding
import com.example.moviesapp.databinding.ModelSimilarMovieItemBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class SearchEpoxyController(
    private val onMovieClick:(Int)-> Unit
):PagingDataEpoxyController<DomainMovieModel>() {

    //when user didn't type any thing yet in search area ,do this
    var localException:SearchDataSource.LocalException?=null
        set(value) {
            field=value
            if (localException !=null){
                //if localException is not null .immediately run "addModels" block of code,otherwise it will run "buildItemModel"
                requestForcedModelBuild()
            }
        }

    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return SearchedMovieEpoxyModel(item!!,onMovieClick)
            .id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (localException != null){
            //show error information
            LocalExceptionErrorEpoxyModel(localException!!).id("error_state").addTo(this)
            return
        }

        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        super.addModels(models)
    }










    data class SearchedMovieEpoxyModel(val item:DomainMovieModel, val onClick:(Int)->Unit)
        : ViewBindingKotlinModel<ModelSimilarMovieItemBinding>(R.layout.model_similar_movie_item) {
        override fun ModelSimilarMovieItemBinding.bind() {
            progressImage.isVisible=true
            ivMovie.load(item!!.poster){
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
            root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    data class LocalExceptionErrorEpoxyModel(val localException:SearchDataSource.LocalException)
        :ViewBindingKotlinModel<ModelExeptionErrorBinding>(R.layout.model_exeption_error){
        override fun ModelExeptionErrorBinding.bind() {
            tvErrorTitle.text=localException.title
            tvErrorDescription.text=localException.description
        }
        //let this to take whole span count
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }
}