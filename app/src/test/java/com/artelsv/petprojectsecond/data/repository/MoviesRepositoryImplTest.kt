package com.artelsv.petprojectsecond.data.repository

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.*
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest {

    private val movieNowPlaying = Movie(false, "", 1000, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000)
    private val moviePopular = Movie(false, "", 1000, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000, movieType = MovieType.POPULAR)

    private val dateRelease = DateRelease("cert", "iso", "11.11.2011", 0)
    private val dateReleaseResultList = DateReleaseResult("iso", listOf(dateRelease, dateRelease))

    private val genre = Genre(0, "Genre")
    private val company = Company(0, "Name", "", "Russia")
    private val country = Country("iso", "Name")
    private val language = Language("EnglishName", "iso", "Name")
    private val movieDetail = MovieDetail(false, "", 0, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000, listOf(genre, genre), "", "", listOf(company, company), listOf(country, country), listOf(language, language), "Ok", "")

    private val moviesRepository: MoviesRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getPopularMovies(MovieSortType.NO) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        every { this@mockk.getPopularMovies(MovieSortType.ASC) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        every { this@mockk.getPopularMovies(MovieSortType.DESC) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))

        every { this@mockk.getNowPlayingMovies(MovieSortType.NO) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        every { this@mockk.getNowPlayingMovies(MovieSortType.ASC) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        every { this@mockk.getNowPlayingMovies(MovieSortType.DESC) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))

        every { this@mockk.getMovieDateRelease(allAny()) } returns Single.just(listOf(dateReleaseResultList, dateReleaseResultList))

        every { this@mockk.getMovieDetails(allAny()) } returns Single.just(movieDetail)
    }

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