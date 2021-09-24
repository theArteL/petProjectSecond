package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import io.reactivex.Single

interface GetMovieDateReleaseUseCase {
    operator fun invoke(movieId: Int, iso: String): Single<DateReleaseResult>
}