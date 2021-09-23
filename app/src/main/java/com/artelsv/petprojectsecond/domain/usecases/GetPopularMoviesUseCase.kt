package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.Movie
import io.reactivex.Single

interface GetPopularMoviesUseCase {
    operator fun invoke(): Single<List<Movie>>
}