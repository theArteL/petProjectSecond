package com.artelsv.petprojectsecond.data.datasource.impl.user.movies

import com.artelsv.petprojectsecond.data.datasource.UserMoviesSource
import com.artelsv.petprojectsecond.data.network.UserMoviesService
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.RateMovieRequest
import com.artelsv.petprojectsecond.data.network.model.ToggleFavoriteRequest
import io.reactivex.Single
import javax.inject.Inject

class UserMoviesRemoteSource @Inject constructor(
    private val userMoviesService: UserMoviesService
) : UserMoviesSource {

    override fun getFavoriteMovies(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userMoviesService.getFavoriteMovies(accountId, sessionId)
    }

    override fun getFavoriteTvShows(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userMoviesService.getFavoriteTv(accountId, sessionId)
    }

    override fun getRatedMovies(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userMoviesService.getRatedMovies(accountId, sessionId)
    }

    override fun getRatedTvShows(accountId: Int, sessionId: String?): Single<MovieListResponse> {
        return userMoviesService.getRatedTvShows(accountId, sessionId)
    }

    override fun toggleFavorite(
        data: ToggleFavoriteRequest,
        accountId: Int,
        sessionId: String?,
    ): Single<Boolean> {
        return userMoviesService.toggleFavorite(accountId, sessionId, data)
            .map { it.success }
    }

    override fun rateMovie(
        data: RateMovieRequest,
        movieId: Int,
        sessionId: String?,
    ): Single<Boolean> {
        return userMoviesService.rateMovie(movieId, sessionId, data)
            .map { it.statusCode == SUCCESS_CODE }
    }

    companion object {
        private const val SUCCESS_CODE = 201
    }
}