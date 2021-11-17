package com.artelsv.petprojectsecond.domain.usecases.impl.movies

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.usecases.movies.GetNowPlayingMoviesUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetNowPlayingMoviesUseCase {
    override fun invoke(sortType: MovieSortType): Flowable<PagingData<Movie>> {
        return moviesRepository.getNowPlayingMovies(sortType)
    }
}