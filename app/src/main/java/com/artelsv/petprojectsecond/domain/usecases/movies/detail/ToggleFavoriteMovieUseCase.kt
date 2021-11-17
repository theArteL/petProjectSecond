package com.artelsv.petprojectsecond.domain.usecases.movies.detail

import io.reactivex.Single

interface ToggleFavoriteMovieUseCase {
    operator fun invoke(accountId: Int, movieId: Int, favorite: Boolean): Single<Boolean>
}