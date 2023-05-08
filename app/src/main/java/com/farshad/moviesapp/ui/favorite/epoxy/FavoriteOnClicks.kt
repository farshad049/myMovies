package com.farshad.moviesapp.ui.favorite.epoxy

import androidx.navigation.NavController
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.ui.favorite.FavoriteFragmentViewModel

class FavoriteOnClicks(
private val nanController: NavController,
private val viewModel: FavoriteFragmentViewModel
) {

    fun onMovieClick(movieId : Int){
        val directions= NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        nanController.navigate(directions)
    }


    fun onCarouselItemClick(item: DomainMovieModel){
        viewModel.changeSelectedItem(item)
    }

    fun onDeleteMovieClick(movie: FavoriteMovieEntity){
        viewModel.deleteFavoriteMovie(movie)
    }
}