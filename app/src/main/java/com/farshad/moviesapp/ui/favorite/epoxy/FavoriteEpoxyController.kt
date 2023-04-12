package com.farshad.moviesapp.ui.favorite.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.farshad.moviesapp.data.model.ui.Resource
import com.farshad.moviesapp.epoxy.LoadingEpoxyModel
import com.farshad.moviesapp.ui.favorite.model.ListAndSelectedData
import java.util.*

class FavoriteEpoxyController(
    private val onClick: FavoriteOnClicks
) : TypedEpoxyController<Resource<ListAndSelectedData>>() {
    override fun buildModels(epoxyData: Resource<ListAndSelectedData>) {
        when (epoxyData) {
            is Resource.Loading -> {
                LoadingEpoxyModel().id(UUID.randomUUID().toString()).addTo(this)
                return
            }
            is Resource.Failure -> {
                EmptyFavoriteMovieListEpoxyModel().id(UUID.randomUUID().toString()).addTo(this)
                return
            }
            is Resource.Success -> {
                HeaderImageEpoxyModel(epoxyData.data.selectedItem.images[1]).id("image-$epoxyData.data.selectedItem.id").addTo(this)

                val listForCarousel= epoxyData.data.list.map {movie->
                    CircleImageEpoxyModel(
                        imageUrl = movie.images.component1(),
                        isSelected = movie.id == epoxyData.data.selectedItem.id,
                        onClick = {onClick.onCarouselItemClick(movie)}
                    ).id("circleImage")
                }

            CarouselModel_().models(listForCarousel).id("Carousel").addTo(this)


            }
        }

    }
}