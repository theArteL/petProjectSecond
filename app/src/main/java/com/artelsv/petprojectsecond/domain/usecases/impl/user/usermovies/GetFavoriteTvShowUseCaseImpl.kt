package com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies

import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteTvShowsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetFavoriteTvShowUseCaseImpl @Inject constructor(
    private val userMoviesRepository: UserMoviesRepository
) : GetFavoriteTvShowsUseCase {

    override fun invoke(accountId: Int): Single<MovieList> {
        return userMoviesRepository.getFavoriteTvShows(accountId)
    }
}