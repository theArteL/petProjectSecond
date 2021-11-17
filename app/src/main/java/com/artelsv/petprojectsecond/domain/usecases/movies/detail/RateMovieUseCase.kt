package com.artelsv.petprojectsecond.domain.usecases.movies.detail

import io.reactivex.Single

interface RateMovieUseCase {
    operator fun invoke(movieId: Int, rating: Number): Single<Boolean>
}