package com.artelsv.petprojectsecond.domain

import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseResultsResponse
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import io.reactivex.Single

interface MoviesRepository {

    fun getPopularMovies() : Single<List<Movie>>
    fun getNowPlayingMovies() : Single<List<Movie>>
    fun getMovieDateRelease(movieId: Int) : Single<List<DateReleaseResult>>
    fun getMovieDetails(movieId: Int) : Single<MovieDetail>
}