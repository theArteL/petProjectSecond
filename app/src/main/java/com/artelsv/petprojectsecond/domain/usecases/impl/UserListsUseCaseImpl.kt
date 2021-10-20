package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.MovieList
import com.artelsv.petprojectsecond.domain.usecases.UserListsUseCase
import io.reactivex.Single
import javax.inject.Inject

class UserListsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UserListsUseCase {
    override fun getFavoriteMovies(): Single<MovieList> {
        return userRepository.getFavoriteMovies()
    }

    override fun getFavoriteTvShows(): Single<MovieList> {
        TODO("Not yet implemented")
    }

    override fun getRatedMovies(): Single<MovieList> {
        TODO("Not yet implemented")
    }

    override fun getRatedTvShows(): Single<MovieList> {
        TODO("Not yet implemented")
    }
}