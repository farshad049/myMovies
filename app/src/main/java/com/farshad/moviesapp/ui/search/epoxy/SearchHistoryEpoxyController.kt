package com.farshad.moviesapp.ui.search.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.epoxy.SearchHistoryEpoxyModel
import com.farshad.moviesapp.data.db.Entity.SearchHistoryEntityWithoutId
import com.farshad.moviesapp.epoxy.VerticalSpaceEpoxyModel
import com.farshad.moviesapp.util.toPx

class SearchHistoryEpoxyController(
    private val onTitleClick : (Int) -> Unit,
    private val onCloseClick : (Int) -> Unit,
    private val onDeleteAllClick : () -> Unit
):TypedEpoxyController<List<SearchHistoryEntityWithoutId>>() {

    override fun buildModels(data: List<SearchHistoryEntityWithoutId>?) {

        if (data?.isNotEmpty() == true){
            DeleteAllEpoxyModel(onDeleteAllClick).id("delete_all").addTo(this)
            VerticalSpaceEpoxyModel(8.toPx()).id("delete_all_bottom_space").addTo(this)
        }

        data?.forEach {
            SearchHistoryEpoxyModel(it , onTitleClick , onCloseClick).id(it.movieId).addTo(this)
        }
    }

}
