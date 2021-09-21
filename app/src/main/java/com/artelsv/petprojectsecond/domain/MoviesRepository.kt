package com.artelsv.petprojectsecond.domain

import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.data.network.model.MovieResponse
import io.reactivex.Single

interface MoviesRepository {

    fun getPopularMovies() : Single<MovieResponse>
    fun getNowPlayingMovies() : Single<MovieResponse>
    fun getMovieDetails(movieId: Int) : Single<MovieDetailResponse>
}