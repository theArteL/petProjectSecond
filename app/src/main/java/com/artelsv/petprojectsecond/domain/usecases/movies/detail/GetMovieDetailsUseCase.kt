package com.artelsv.petprojectsecond.domain.usecases.movies.detail

import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import io.reactivex.Single

interface GetMovieDetailsUseCase {
    operator fun invoke(movieId: Int): Single<MovieDetail>
}