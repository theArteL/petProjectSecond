package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.model.MovieType
import io.reactivex.Single

interface MovieDataSource {

    fun getPopularMovies() : Single<List<Movie>>
    fun getNowPlayingMovies() : Single<List<Movie>>
    fun getMovieDateRelease(movieId: Int) : Single<List<DateReleaseResult>>
    fun getMovieDetails(movieId: Int) : Single<MovieDetail>
    fun addMoviesToDb(data: List<Movie>, type: MovieType): List<Movie>
}