package com.artelsv.petprojectsecond.domain

import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import io.reactivex.Single

interface MoviesRepository {

    fun getPopularMovies() : Single<List<Movie>>
    fun getNowPlayingMovies() : Single<List<Movie>>
    fun getMovieDetails(movieId: Int) : Single<MovieDetail>
}