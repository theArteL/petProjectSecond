package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.data.network.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular?page=1")
    fun getPopularMovies() : Single<MovieResponse>

    @GET("movie/now_playing?page=1")
    fun getNowPlayingMovies() : Single<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") movieId: Int) : Single<MovieDetailResponse>
}