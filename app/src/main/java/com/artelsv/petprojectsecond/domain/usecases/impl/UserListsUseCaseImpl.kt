package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.usecases.UserListsUseCase
import io.reactivex.Single
import javax.inject.Inject

class UserListsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UserListsUseCase {
    override fun getFavoriteMovies(accountId: Int): Single<MovieList> {
        return userRepository.getFavoriteMovies(accountId)
    }

    override fun getFavoriteTvShows(accountId: Int): Single<MovieList> {
        return userRepository.getFavoriteTvShows(accountId)
    }

    override fun getRatedMovies(accountId: Int): Single<MovieList> {
        return userRepository.getFavoriteMovies(accountId)
    }

    override fun getRatedTvShows(accountId: Int): Single<MovieList> {
        return userRepository.getFavoriteMovies(accountId)
    }
}