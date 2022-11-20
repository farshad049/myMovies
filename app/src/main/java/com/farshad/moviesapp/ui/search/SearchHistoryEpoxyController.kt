package com.farshad.moviesapp.ui.search

import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.epoxy.DeleteAllEpoxyModel
import com.farshad.moviesapp.epoxy.SearchHistoryEpoxyModel
import com.farshad.moviesapp.data.db.Entity.SearchHistoryEntityWithoutId

class SearchHistoryEpoxyController(
    private val onTitleClick : (Int) -> Unit,
    private val onCloseClick : (Int) -> Unit,
    private val onDeleteAllClick : () -> Unit
):TypedEpoxyController<List<SearchHistoryEntityWithoutId>>() {

    override fun buildModels(data: List<SearchHistoryEntityWithoutId>?) {

        if (data?.isNotEmpty() == true){
            DeleteAllEpoxyModel(onDeleteAllClick).id("delete_all").addTo(this)
        }

        data?.forEach {
            SearchHistoryEpoxyModel(it , onTitleClick , onCloseClick).id(it.movieId).addTo(this)
        }
    }

}
