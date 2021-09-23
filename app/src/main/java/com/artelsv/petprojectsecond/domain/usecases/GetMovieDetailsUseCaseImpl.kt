package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDetailsUseCase {
    override fun invoke(movieId: Int): Single<MovieDetail> = moviesRepository.getMovieDetails(movieId)
}