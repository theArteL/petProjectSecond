package com.artelsv.petprojectsecond.domain.usecases.movies.detail

import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import io.reactivex.Single

interface GetMovieCreditsUseCase {
    operator fun invoke(movieId: Int): Single<Credits>
}