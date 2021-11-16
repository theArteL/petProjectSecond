package com.artelsv.petprojectsecond.domain.usecases

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetNowPlayingMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.movies.GetNowPlayingMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNowPlayingMoviesUseCaseTest {
    private val movieNowPlaying = Movie(false, "", 1000, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000)

    private val moviesRepository: MoviesRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getNowPlayingMovies(MovieSortType.NO) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        every { this@mockk.getNowPlayingMovies(MovieSortType.ASC) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        every { this@mockk.getNowPlayingMovies(MovieSortType.DESC) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
    }

    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCaseImpl(moviesRepository)

    @Test
    fun invoke_SortTypeNo_False() {
        getNowPlayingMoviesUseCase(MovieSortType.NO).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun invoke_SortTypeAsc_False() {
        getNowPlayingMoviesUseCase(MovieSortType.ASC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun invoke_SortTypeDesc_False() {
        getNowPlayingMoviesUseCase(MovieSortType.DESC).isEmpty.map {
            assertEquals(false, it)
        }
    }
}