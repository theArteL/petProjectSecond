package com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail

import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieDetailsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : GetMovieDetailsUseCase {

    override fun invoke(movieId: Int): Single<MovieDetail> =
        moviesRepository.getMovieDetails(movieId)
}