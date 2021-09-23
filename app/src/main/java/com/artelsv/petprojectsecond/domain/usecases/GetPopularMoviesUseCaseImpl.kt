package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.Movie
import io.reactivex.Single
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetPopularMoviesUseCase {
    override fun invoke(): Single<List<Movie>> = moviesRepository.getPopularMovies().map { it.sortedBy { sort -> sort.voteAverage } }
}