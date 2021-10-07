package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDetailsUseCase {
    override fun invoke(movieId: Int): Single<MovieDetail> = moviesRepository.getMovieDetails(movieId)
}