package com.farshad.moviesapp.ui.dashboard.epoxy


import android.content.Context
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelGenreIconBinding
import com.farshad.moviesapp.databinding.ModelImageItemBinding
import com.farshad.moviesapp.databinding.ModelMovieThumbnailBinding
import com.farshad.moviesapp.epoxy.*
import com.farshad.moviesapp.data.model.domain.DomainMovieModel
import com.farshad.moviesapp.data.model.network.GenresModel
import java.util.*


class DashboardEpoxyController(
    private val context : Context ,
    private val onClicks: DashboardOnClicks
   // private val onItemClick: OnClickInterface
): Typed2EpoxyController<List<DomainMovieModel>,List<GenresModel>>() {


    override fun buildModels(data1: List<DomainMovieModel>,data2: List<GenresModel>) {

        if (data1.isEmpty() || data2.isEmpty()){
            repeat(4){ModelShimmerDashboard().id(UUID.randomUUID().toString()).addTo(this)}
            return
        }


        HeaderEpoxyModel(context.getString(R.string.random_movies)).id(UUID.randomUUID().toString()).addTo(this)


        val list1= data1.random().let { randomItem->
            randomItem.images.map {
                RandomImageModel(
                    imageUrl = it,
                    id = randomItem.id,
                    onclick = {movieId -> onClicks.onMovieClick(movieId) }
                ).id(randomItem.id) }
        }
        CarouselModel_()
            .id(UUID.randomUUID().toString())
            .models(list1)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)


        HeaderEpoxyModel(context.getString(R.string.top_rated_movies)).id(UUID.randomUUID().toString()).addTo(this)




        val topRatedMovieList= data1.map {
            MovieThumbnailModel(
                item = it,
                onclick = {movieId-> onClicks.onMovieClick(movieId) }
            ).id(it.id)
        }
        CarouselModel_()
            .id(UUID.randomUUID().toString())
            .models(topRatedMovieList)
            .numViewsToShowOnScreen(2.8f)
            .addTo(this)

        HeaderEpoxyModel(context.getString(R.string.movies_by_genre)).id(UUID.randomUUID().toString()).addTo(this)




        val genresList= data2.map {
            GenreIconModel(
                genre = it,
                onclick = {genreId, genreName -> onClicks.onGenreClick(genreId , genreName)}
            ).id(it.id)
        }
        CarouselModel_()
            .id(UUID.randomUUID().toString())
            .models(genresList)
            .numViewsToShowOnScreen(3.1f)
            .addTo(this)










    }


    data class RandomImageModel(val imageUrl:String,val id:Int,val onclick: (Int) -> Unit)
        : ViewBindingKotlinModel<ModelImageItemBinding>(R.layout.model_image_item){
        override fun ModelImageItemBinding.bind(){
            progressImage.isVisible=true
            imageView.load(imageUrl){
                crossfade(500)
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
            root.setOnClickListener { onclick(id) }
        }
    }


    data class MovieThumbnailModel(val item: DomainMovieModel, val onclick: (Int) -> Unit)
        : ViewBindingKotlinModel<ModelMovieThumbnailBinding>(R.layout.model_movie_thumbnail){
        override fun ModelMovieThumbnailBinding.bind(){
            progressImage.isVisible=true
            ivMovie.load(item.poster){
                crossfade(500)
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
            root.setOnClickListener { onclick(item.id) }
        }
    }




    data class GenreIconModel(val genre:GenresModel,val onclick: (Int, String) -> Unit )
        : ViewBindingKotlinModel<ModelGenreIconBinding>(R.layout.model_genre_icon){
        override fun ModelGenreIconBinding.bind(){

            val imageRes= when(genre.id){
                1 -> R.drawable.crime
                2 -> R.drawable.drama
                3 -> R.drawable.action
                4 -> R.drawable.biography
                5 -> R.drawable.history
                6 -> R.drawable.adventure
                7 -> R.drawable.fantasy
                8 -> R.drawable.western
                9 -> R.drawable.comedy
                10 -> R.drawable.sci_fi
                11 -> R.drawable.mystery
                12 -> R.drawable.thriller
                13 -> R.drawable.family
                14 -> R.drawable.war
                15 -> R.drawable.animation
                16 -> R.drawable.romance
                17 -> R.drawable.horror
                18 -> R.drawable.music
                19 -> R.drawable.film_noir
                20 -> R.drawable.musical
                21 -> R.drawable.sport
                else -> R.drawable.sport
            }

            progressImage.isVisible=true
            imageView.load(imageRes){
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
            tvGenreName.text= genre.name
            root.setOnClickListener { onclick(genre.id,genre.name) }
        }
    }










}