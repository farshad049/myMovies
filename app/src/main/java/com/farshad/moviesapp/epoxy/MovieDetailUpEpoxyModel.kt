package com.farshad.moviesapp.epoxy

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import coil.load
import com.farshad.moviesapp.R
import com.farshad.moviesapp.data.db.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.databinding.ModelDetailFragmentUpBinding
import com.farshad.moviesapp.ui.favorite.FavoriteFragmentViewModel

data class MovieDetailUpEpoxyModel (
    val context : Context,
    val uiModel : DomainMovieModel,
    val favoriteFragmentViewModel : FavoriteFragmentViewModel,
    val isFavorite : Boolean
        ):ViewBindingKotlinModel<ModelDetailFragmentUpBinding>(R.layout.model_detail_fragment_up) {
    override fun ModelDetailFragmentUpBinding.bind() {

        progressOnPoster.isVisible=true
        ivMovie.load(uiModel.poster){
            listener { request, result ->
                progressOnPoster.isGone=true
            }
        }

        tvMovieName.text= uiModel.title
        tvIMDB.text= uiModel.imdb_rating
        tvYear.text= uiModel.year
        tvRate.text= uiModel.rated
        tvCountry.text= uiModel.country
        tvDirector.text= uiModel.director
        tvGenres.text= convertListToText(uiModel.genres)
        tvActors.text= uiModel.actors
        tvPlot.text= uiModel.plot

        val imageRes=if (isFavorite) R.drawable.ic_round_favorite_24 else R.drawable.ic_round_favorite_border_24

        favoriteImage.load(imageRes)

                favoriteImage.setOnClickListener {
            if (isFavorite){
                favoriteFragmentViewModel.deleteFavoriteMovie(
                    FavoriteMovieEntity(
                        id = uiModel.id ,
                        title = uiModel.title
                    )
                )
            }else{
                favoriteFragmentViewModel.insertFavoriteMovie(
                    FavoriteMovieEntity(
                        id = uiModel.id,
                        title = uiModel.title
                    )
                )
            }
        }

        btnShare.setOnClickListener {
            val dataToShare = "https://moviesapi.ir/api/v1/movies/${uiModel.id}"
            val intent= Intent()
            intent.action= Intent.ACTION_SEND
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hey Check out this Great app:")
            intent.putExtra(Intent.EXTRA_TEXT, dataToShare)
            startActivity(context , Intent.createChooser(intent,"Share To:") , null)
        }

    }










    private fun convertListToText(list : List<String>): String{
        val sb = StringBuilder()
        for (element in list) {
            sb.append(element).append(", ")
        }
        return sb.toString()
    }
}