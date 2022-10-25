package com.example.moviesapp.ui.search

import com.airbnb.epoxy.TypedEpoxyController
import com.example.moviesapp.epoxy.SearchHistoryEpoxyModel
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntity
import com.example.moviesapp.roomDatabase.Entity.SearchHistoryEntityWithoutId

class SearchHistoryEpoxyController(
    private val onTitleClick: (Int) -> Unit,
    private val onCloseClick: (Int) -> Unit
):TypedEpoxyController<List<SearchHistoryEntityWithoutId>>() {

    override fun buildModels(data: List<SearchHistoryEntityWithoutId>?) {
        data?.forEach {
            SearchHistoryEpoxyModel(it , onTitleClick , onCloseClick).id(it.movieId).addTo(this)
        }
    }

}
