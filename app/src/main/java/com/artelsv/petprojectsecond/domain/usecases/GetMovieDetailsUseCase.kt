package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.MovieDetail
import io.reactivex.Single

interface GetMovieDetailsUseCase {
    operator fun invoke(movieId: Int): Single<MovieDetail>
}