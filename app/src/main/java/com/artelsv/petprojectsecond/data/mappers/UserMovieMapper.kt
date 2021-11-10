package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.entity.UserMovieEntity
import com.artelsv.petprojectsecond.domain.model.movie.Movie

object UserMovieMapper {

    fun toEntity(data: Movie, isFavorite: Boolean = false) = UserMovieEntity(
        id = data.id,
        adult = data.adult,
        backdropPath = data.backdropPath,
        budget = data.budget,
        genreIds = data.genreIds,
        originalLanguage = data.originalLanguage,
        originalTitle = data.originalTitle,
        overview = data.overview,
        popularity = data.popularity,
        posterPath = data.posterPath,
        releaseDate = data.releaseDate,
        revenue = data.revenue,
        runtime = data.runtime,
        title = data.title,
        video = data.video,
        voteAverage = data.voteAverage,
        voteCount = data.voteCount,

        movieType = data.movieType,
        favorite = isFavorite,
        rating = data.rating
    )
}