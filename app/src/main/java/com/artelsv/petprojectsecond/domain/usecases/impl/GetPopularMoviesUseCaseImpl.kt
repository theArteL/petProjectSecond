package com.artelsv.petprojectsecond.domain.usecases.impl

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetPopularMoviesUseCase {
    override fun invoke(sortType: MovieSortType): Flowable<PagingData<Movie>> {
        return moviesRepository.getPopularMovies(sortType)
    }
}