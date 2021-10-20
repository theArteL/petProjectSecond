package com.artelsv.petprojectsecond.domain

import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.domain.model.MovieList
import com.artelsv.petprojectsecond.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<String>

    fun getUser(): Single<User>
    fun getLocalUser(): User?
    fun exit()

    fun getFavoriteMovies() : Single<MovieList>
    fun getFavoriteTvShows() : Single<MovieList>
}