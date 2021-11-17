package com.artelsv.petprojectsecond.data.network.model.persondetail

import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.google.gson.annotations.SerializedName

data class MoviesPersonDetailResponse(
    @SerializedName("cast") val cast: List<MovieCastResponse>,
    @SerializedName("crew") val crew: List<MovieCrewResponse>,
    @SerializedName("id") val id: Int,
)

fun MoviesPersonDetailResponse.toModel() =
    this.cast.map { it.toModel() } + this.crew.map { it.toModel() }

private fun MovieCastResponse.toModel() = Movie(
    this.adult,
    this.backdropPath,
    0,
    this.genreIds,
    this.id,
    this.originalLanguage,
    this.originalTitle,
    this.overview,
    this.popularity,
    this.posterPath,
    this.releaseDate,
    0,
    0,
    this.title,
    this.video,
    this.voteAverage,
    this.voteCount
)

private fun MovieCrewResponse.toModel() = Movie(
    this.adult,
    this.backdropPath,
    0,
    this.genreIds,
    this.id,
    this.originalLanguage,
    this.originalTitle,
    this.overview,
    this.popularity,
    this.posterPath,
    this.releaseDate,
    0,
    0,
    this.title,
    this.video,
    this.voteAverage,
    this.voteCount
)