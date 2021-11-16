package com.artelsv.petprojectsecond.domain.repository

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository {

    fun getPopularMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>>
    fun getNowPlayingMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>>
    fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>>
    fun getMovieDetails(movieId: Int): Single<MovieDetail>
    fun getMovieCredits(movieId: Int): Single<Credits>
    fun getMoviesByCredits(personId: Int): Single<List<Movie>>
    fun getPersonDetail(personId: Int): Single<PersonDetail>
}