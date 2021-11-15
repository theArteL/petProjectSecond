package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.movie.CreditsResponse
import com.artelsv.petprojectsecond.data.network.model.persondetail.MoviesPersonDetailResponse
import com.artelsv.petprojectsecond.data.network.model.persondetail.PersonDetailResponse
import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MovieListResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int): Single<MovieListResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") movieId: Int): Single<MovieDetailResponse>

    @GET("movie/{id}/release_dates")
    fun getMovieReleaseDates(@Path("id") movieId: Int): Single<DateReleaseListResponse>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") movieId: Int): Single<CreditsResponse>

    @GET("person/{id}/movie_credits")
    fun getMoviesByCredits(@Path("id") personId: Int): Single<MoviesPersonDetailResponse>

    @GET("person/{id}")
    fun getPersonDetail(@Path("id") personId: Int): Single<PersonDetailResponse>
}