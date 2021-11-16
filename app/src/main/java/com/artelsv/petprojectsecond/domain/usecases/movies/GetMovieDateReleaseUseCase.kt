package com.artelsv.petprojectsecond.domain.usecases.movies

import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import io.reactivex.Single

interface GetMovieDateReleaseUseCase {
    operator fun invoke(movieId: Int, iso: String): Single<DateReleaseResult>
}