package com.example.moviesapp.model.mapper

import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.model.network.NetworkMovieModel
import java.math.RoundingMode
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun buildFrom(networkMovieModel: NetworkMovieModel): DomainMovieModel {
        return DomainMovieModel(
            actors=networkMovieModel.actors,
            country=networkMovieModel.country,
            director=networkMovieModel.director,
            genres=networkMovieModel.genres,
            id=networkMovieModel.id,
            images=networkMovieModel.images,
            imdb_rating=networkMovieModel.imdb_rating,
            plot= networkMovieModel.plot,
            poster=networkMovieModel.poster,
            rated=networkMovieModel.rated,
            title=networkMovieModel.title,
            year=networkMovieModel.year
        )
    }
}