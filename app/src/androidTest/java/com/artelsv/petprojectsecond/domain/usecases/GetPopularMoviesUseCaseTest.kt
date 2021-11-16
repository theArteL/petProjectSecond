package com.artelsv.petprojectsecond.domain.usecases

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetPopularMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.movies.GetPopularMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPopularMoviesUseCaseTest {
    private val moviePopular = Movie(false, "", 1000, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000, movieType = MovieType.POPULAR)

    private val moviesRepository: MoviesRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getPopularMovies(MovieSortType.NO) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        every { this@mockk.getPopularMovies(MovieSortType.ASC) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        every { this@mockk.getPopularMovies(MovieSortType.DESC) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
    }

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = GetPopularMoviesUseCaseImpl(moviesRepository)

    @Test
    fun invoke_SortTypeNo_False() {
        getPopularMoviesUseCase(MovieSortType.NO).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun invoke_SortTypeAsc_False() {
        getPopularMoviesUseCase(MovieSortType.ASC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun invoke_SortTypeDesc_False() {
        getPopularMoviesUseCase(MovieSortType.DESC).isEmpty.map {
            assertEquals(false, it)
        }
    }
}