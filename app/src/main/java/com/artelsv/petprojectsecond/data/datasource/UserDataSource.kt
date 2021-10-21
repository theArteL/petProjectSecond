package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.ToggleFavoriteRequest
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single

interface UserDataSource {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String,
    ): Single<String>

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
}