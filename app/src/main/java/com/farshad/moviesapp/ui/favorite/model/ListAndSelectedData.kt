package com.farshad.moviesapp.ui.favorite.model

import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.ui.Resource

data class ListAndSelectedData(
    val list:List<DomainMovieModel>,
    val selectedItem: DomainMovieModel
)
