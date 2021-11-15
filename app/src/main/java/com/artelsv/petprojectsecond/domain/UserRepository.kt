package com.artelsv.petprojectsecond.domain

import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.model.movie.RateMovie
import com.artelsv.petprojectsecond.domain.model.movie.ToggleFavorite
import com.artelsv.petprojectsecond.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String,
    ): Single<String>

    fun getUser(): Single<User>
    fun getLocalUser(): User?
    fun exit()

    fun getFavoriteMovies(accountId: Int): Single<MovieList>
    fun getFavoriteTvShows(accountId: Int): Single<MovieList>
    fun getRatedMovies(accountId: Int): Single<MovieList>
    fun getRatedTvShows(accountId: Int): Single<MovieList>

    fun toggleFavorite(data: ToggleFavorite, accountId: Int): Single<Boolean>
    fun rateMovie(data: RateMovie, movieId: Int): Single<Boolean>
}