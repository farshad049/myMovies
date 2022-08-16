package com.example.moviesapp.ui.search

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelMovieListItemBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.MovieListModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class SearchEpoxyController(
    private val onMovieClick:(Int)-> Unit
):PagingDataEpoxyController<DomainMovieModel>() {



    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModel(item!!,onMovieClick)
            .id("search${item.id}")
    }

    override fun addModels(models: List<EpoxyModel<*>>) {


        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading3").addTo(this)
            return
        }
        super.addModels(models)
    }













}