package com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail

import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieCreditsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieCreditsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : GetMovieCreditsUseCase {

    override fun invoke(movieId: Int): Single<Credits> =
        moviesRepository.getMovieCredits(movieId)
}