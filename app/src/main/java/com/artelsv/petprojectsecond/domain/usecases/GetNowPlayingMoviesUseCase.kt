package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.Movie
import io.reactivex.Single

interface GetNowPlayingMoviesUseCase {
    operator fun invoke(): Single<List<Movie>>
}