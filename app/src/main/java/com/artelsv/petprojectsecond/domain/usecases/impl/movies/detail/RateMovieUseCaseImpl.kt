package com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail

import com.artelsv.petprojectsecond.domain.model.movie.RateMovie
import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.RateMovieUseCase
import io.reactivex.Single
import javax.inject.Inject

class RateMovieUseCaseImpl @Inject constructor(
    private val userMoviesRepository: UserMoviesRepository,
) : RateMovieUseCase {

    override fun invoke(movieId: Int, rating: Number): Single<Boolean> {
        return userMoviesRepository.rateMovie(
            RateMovie(rating),
            movieId
        )
    }
}