package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") Page: Int) : Single<MovieListResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") Page: Int) : Single<MovieListResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") movieId: Int) : Single<MovieDetailResponse>

    @GET("movie/{id}/release_dates")
    fun getMovieReleaseDates(@Path("id") movieId: Int) : Single<DateReleaseListResponse>
}