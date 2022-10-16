package com.example.moviesapp.ui.dashboard


import android.content.Context
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelGenreIconBinding
import com.example.moviesapp.databinding.ModelImageItemBinding
import com.example.moviesapp.databinding.ModelMovieThumbnailBinding
import com.example.moviesapp.epoxy.*
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.network.GenresModel



class DashboardEpoxyController(
    private val context : Context ,
    private val onItemClick:OnClickInterface
): Typed2EpoxyController<List<DomainMovieModel?>,List<GenresModel?>>() {


    override fun buildModels(data1: List<DomainMovieModel?>,data2: List<GenresModel?>) {

        if (data1.isEmpty() || data2.isEmpty()){
            LoadingEpoxyModel().id("loading4").addTo(this)
            return
        }


        HeaderEpoxyModel(context.getString(R.string.random_movies)).id("random_movies").addTo(this)


        val list1=data1.random()!!.let { randomItem->
            randomItem.images.map { RandomImageModel(it,randomItem.id, onItemClick).id(randomItem.id) }
        }
        CarouselModel_()
            .id("random_image_carousel")
            .models(list1)
            .numViewsToShowOnScreen(1.25f)
            .addTo(this)


        HeaderEpoxyModel(context.getString(R.string.top_rated_movies)).id("top_rated_movies").addTo(this)




        val topRatedMovieList= data1.map {
            MovieModel(it,onItemClick).id(it?.id)
        }
        CarouselModel_()
            .id("top_rated_carousel")
            .models(topRatedMovieList)
            .numViewsToShowOnScreen(2.9f)
            .addTo(this)

        HeaderEpoxyModel(context.getString(R.string.movies_by_genre)).id("movies_by_genre").addTo(this)




        val genresList= data2.map {
            GenreIconModel(it!!,onItemClick).id(it.id)
        }
        CarouselModel_()
            .id("genres_icon_carousel")
            .models(genresList)
            .numViewsToShowOnScreen(2.9f)
            .addTo(this)










    }


    data class RandomImageModel(val imageUrl:String,val id:Int,val onclick:OnClickInterface)
        : ViewBindingKotlinModel<ModelImageItemBinding>(R.layout.model_image_item){
        override fun ModelImageItemBinding.bind(){
            progressImage.isVisible=true
            imageView.load(imageUrl){
                crossfade(500)
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
            root.setOnClickListener { onclick.onMovieClick(id) }
        }
    }


    data class MovieModel(val item: DomainMovieModel?, val onclick:OnClickInterface)
        : ViewBindingKotlinModel<ModelMovieThumbnailBinding>(R.layout.model_movie_thumbnail){
        override fun ModelMovieThumbnailBinding.bind(){
            progressImage.isVisible=true
            ivMovie.load(item!!.poster){
                crossfade(500)
                listener { request, result ->
                    progressImage.isGone=true
                }
            }
            root.setOnClickListener { onclick.onMovieClick(item.id) }
        }
    }




    data class GenreIconModel(val genre:GenresModel,val onclick:OnClickInterface)
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
            tvGenreName.text=genre.name
            root.setOnClickListener { onclick.onGenreClick(genre.id,genre.name) }
        }
    }










}