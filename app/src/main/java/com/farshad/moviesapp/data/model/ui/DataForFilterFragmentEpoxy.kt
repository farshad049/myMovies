package com.farshad.moviesapp.data.model.ui

data class DataForFilterFragmentEpoxy(
    val filteredByGenreList : IsExpandAndList,
    val filteredByImdbList : IsExpandAndList
)
{
    data class IsExpandAndList(
        val isExpand : Boolean ,
        val filterList : List<UiFilter>
    )
}
