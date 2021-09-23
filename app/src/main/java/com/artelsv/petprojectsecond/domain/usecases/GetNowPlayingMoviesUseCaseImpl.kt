package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.Movie
import io.reactivex.Single
import javax.inject.Inject

class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetNowPlayingMoviesUseCase {
    override fun invoke(): Single<List<Movie>> = moviesRepository.getNowPlayingMovies().map { it.sortedBy { sort -> sort.voteAverage } }
}