package com.artelsv.petprojectsecond.domain.usecases.impl.movies

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.usecases.movies.GetPopularMoviesUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetPopularMoviesUseCase {
    override fun invoke(sortType: MovieSortType): Flowable<PagingData<Movie>> {
        return moviesRepository.getPopularMovies(sortType)
    }
}