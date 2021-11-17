package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import io.reactivex.Single

interface MovieDataSource {

    fun getPopularMovies(page: Int): Single<List<Movie>>
    fun getNowPlayingMovies(page: Int): Single<List<Movie>>
    fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>>
    fun getMovieDetails(movieId: Int): Single<MovieDetail>
    fun addMoviesToDb(data: List<Movie>, type: MovieType): List<Movie>

    fun getMovieCredits(movieId: Int): Single<Credits>
    fun getMoviesByCredits(personId: Int): Single<List<Movie>>
    fun getPersonDetail(personId: Int): Single<PersonDetail>
}