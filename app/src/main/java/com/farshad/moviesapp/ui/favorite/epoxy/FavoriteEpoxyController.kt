package com.farshad.moviesapp.ui.favorite.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.data.model.ui.Resource
import com.farshad.moviesapp.epoxy.DividerEpoxyModel
import com.farshad.moviesapp.epoxy.LoadingEpoxyModel
import com.farshad.moviesapp.epoxy.VerticalSpaceEpoxyModel
import com.farshad.moviesapp.ui.dashboard.epoxy.ModelShimmerDashboard
import com.farshad.moviesapp.ui.favorite.model.ListAndSelectedData
import java.util.*

class FavoriteEpoxyController(
    private val onClick: FavoriteOnClicks
) : TypedEpoxyController<Resource<ListAndSelectedData>>() {
    override fun buildModels(epoxyData: Resource<ListAndSelectedData>) {
        when (epoxyData) {
            is Resource.Loading -> {
                repeat(4){ ModelShimmerFavorite().id(UUID.randomUUID().toString()).addTo(this)}
                return
            }
            is Resource.Failure -> {
                EmptyFavoriteMovieListEpoxyModel().id(UUID.randomUUID().toString()).addTo(this)
                return
            }
            is Resource.Success -> {

                HeaderImageEpoxyModel(
                    imageUrl = epoxyData.data.selectedItem.images[1],
                    onClick = {onClick.onMovieClick(epoxyData.data.selectedItem.id)}
                )
                    .id("image-$epoxyData.data.selectedItem.id")
                    .addTo(this)

                DividerEpoxyModel(verticalMargin = 2).id("favorite_divider").addTo(this)

                val listForCarousel= epoxyData.data.list.map {movie->
                    CircleImageEpoxyModel(
                        imageUrl = movie.images.component1(),
                        isSelected = movie.id == epoxyData.data.selectedItem.id,
                        onClick = {onClick.onCarouselItemClick(movie)}
                    ).id("circleImage${movie.id}")
                }

                CarouselModel_().
                models(listForCarousel).
                id("Carousel").
                numViewsToShowOnScreen(4.8f).
                addTo(this)


                VerticalSpaceEpoxyModel(6).id("favorite_space").addTo(this)


                FavoriteFooterModel(
                    movie = epoxyData.data.selectedItem,
                    onClick = {movieEntity ->
                        onClick.onDeleteMovieClick(movieEntity)
                    }
                )
                    .id("favorite_footer")
                    .addTo(this)




            }
        }



    }
}