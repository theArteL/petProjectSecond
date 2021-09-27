package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import io.reactivex.Single

interface GetNowPlayingMoviesUseCase {
    operator fun invoke(sortType: MovieSortType): Single<List<Movie>>
}