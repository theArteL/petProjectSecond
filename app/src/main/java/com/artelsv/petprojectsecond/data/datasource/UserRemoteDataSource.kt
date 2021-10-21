package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.UserService
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import com.artelsv.petprojectsecond.domain.model.MovieList
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override fun createGuestSession(): Single<String> {
        return userService.createGuestSession().map {
            it.guestSessionId
        }
    }

    override fun createRequestToken(): Single<String> {
        return userService.createRequestToken().map {
            it.requestToken
        }
    }

    override fun createSession(requestToken: String): Single<String> {
        return userService.createSession(
            mapOf(
                "request_token" to requestToken
            )
        ).map { it.sessionId }
    }

    override fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<String> {
        return userService.createSessionWithUser(
            hashMapOf(
                "username" to login,
                "password" to password,
                "request_token" to requestToken
            )
        ).map { it.requestToken }
    }

    override fun getUser(sessionId: String): Single<UserResponse> {
        return userService.getUser(
            sessionId
        )
    }

    override fun getFavoriteMovies(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userService.getFavoriteMovies(accountId, sessionId)
    }

    override fun getFavoriteTvShows(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userService.getFavoriteTv(accountId, sessionId)
    }

    override fun getRatedMovies(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userService.getRatedMovies(accountId, sessionId)
    }

    override fun getRatedTvShows(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userService.getRatedTvShows(accountId, sessionId)
    }
}