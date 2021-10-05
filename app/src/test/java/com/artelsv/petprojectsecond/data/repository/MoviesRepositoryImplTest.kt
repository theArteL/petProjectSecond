package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.fakerepositories.FakeMovieRepository
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest {

    private val moviesRepository: MoviesRepository = FakeMovieRepository()

    @Test
    fun getPopularMovies() {
        moviesRepository.getPopularMovies(MovieSortType.NO).isEmpty.map {
            assertEquals(false, it)
        }

        moviesRepository.getPopularMovies(MovieSortType.ASC).isEmpty.map {
            assertEquals(false, it)
        }

        moviesRepository.getPopularMovies(MovieSortType.DESC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getNowPlayingMovies() {
        moviesRepository.getNowPlayingMovies(MovieSortType.NO).isEmpty.map {
            assertEquals(false, it)
        }

        moviesRepository.getNowPlayingMovies(MovieSortType.ASC).isEmpty.map {
            assertEquals(false, it)
        }

        moviesRepository.getNowPlayingMovies(MovieSortType.DESC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getMovieDateRelease() {
        for (i in 0..1000) {
            moviesRepository.getMovieDateRelease(i).map { data ->
                assert(!data.isNullOrEmpty())
            }
        }
    }

    @Test
    fun getMovieDetails() {
        for (i in 0..1000) {
            moviesRepository.getMovieDetails(i).map { data ->
                assert(data.id > 0 && data.title != "")
            }
        }
    }
}