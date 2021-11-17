package com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies

import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedTvShowsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRatedTvShowsUseCaseImpl @Inject constructor(
    private val userMoviesRepository: UserMoviesRepository
) : GetRatedTvShowsUseCase{

    override fun invoke(accountId: Int): Single<MovieList> {
        return userMoviesRepository.getFavoriteMovies(accountId)
    }
}