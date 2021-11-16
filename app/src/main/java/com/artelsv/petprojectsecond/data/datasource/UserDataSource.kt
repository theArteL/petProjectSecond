package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.RateMovieRequest
import com.artelsv.petprojectsecond.data.network.model.ToggleFavoriteRequest
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single

interface UserDataSource {
    fun getUser(sessionId: String): Single<UserResponse>

    fun getFavoriteMovies(accountId: Int, sessionId: String?): Single<MovieListResponse>
    fun getFavoriteTvShows(accountId: Int, sessionId: String?): Single<MovieListResponse>
    fun getRatedMovies(accountId: Int, sessionId: String?): Single<MovieListResponse>
    fun getRatedTvShows(accountId: Int, sessionId: String?): Single<MovieListResponse>

    fun toggleFavorite(
        data: ToggleFavoriteRequest,
        accountId: Int,
        sessionId: String?,
    ): Single<Boolean>

    fun rateMovie(
        data: RateMovieRequest,
        movieId: Int,
        sessionId: String?,
    ): Single<Boolean>
}