package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.MovieDetail
import io.reactivex.Single

interface GetMovieDetailsUseCase {
    operator fun invoke(movieId: Int): Single<MovieDetail>

    fun rate(movieId: Int, rating: Number): Single<Boolean>
    fun favorite(movieId: Int, favorite: Boolean): Single<Boolean>
}