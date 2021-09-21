package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.entity.MovieEntity
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieType

class MovieMapper(val type: MovieType? = null) {

    fun toMovieEntity(movie: Movie) : MovieEntity {
        return MovieEntity(
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            budget = movie.budget,
            genreIds = movie.genreIds,
            id = movie.id,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            movieType = type ?: movie.movieType
        )
    }

    fun toMovie(movie: MovieEntity) : Movie {
        return Movie(
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            budget = movie.budget,
            genreIds = movie.genreIds,
            id = movie.id,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            movieType = type ?: movie.movieType
        )
    }

    fun toListMovieEntity(movies: Collection<Movie>) : List<MovieEntity> {
        val out = arrayListOf<MovieEntity>()

        for (item in movies) {
            out.add(toMovieEntity(item))
        }

        return out
    }

    fun toListMovie(movies: Collection<MovieEntity>) : List<Movie> {
        val out = arrayListOf<Movie>()

        for (item in movies) {
            out.add(toMovie(item))
        }

        return out
    }
}