package com.artelsv.petprojectsecond.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import com.artelsv.petprojectsecond.utils.Constants.DATABASE_MOVIE_TABLE

@Entity(tableName = DATABASE_MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Int,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,

    val movieType: MovieType = MovieType.NOW_PLAYING
)