package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import io.reactivex.Single
import javax.inject.Inject

class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetNowPlayingMoviesUseCase {
    override fun invoke(sortType: MovieSortType): Single<List<Movie>> {
        return when(sortType) {
            MovieSortType.ASC -> moviesRepository.getNowPlayingMovies().map { it.sortedBy { sort -> sort.voteAverage } }
            MovieSortType.DESC -> moviesRepository.getNowPlayingMovies().map { it.sortedByDescending { sort -> sort.voteAverage } }
            MovieSortType.NO -> moviesRepository.getNowPlayingMovies()
        }
    }
}