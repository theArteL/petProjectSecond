package com.artelsv.petprojectsecond.domain

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.*
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository {

    fun getPopularMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>>
    fun getNowPlayingMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>>
    fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>>
    fun getMovieDetails(movieId: Int): Single<MovieDetail>
}