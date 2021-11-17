package com.artelsv.petprojectsecond.data.repository

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.model.movie.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
    private val movieDetail = MovieDetail(false, "", 0, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0f, 1000, listOf(genre, genre), "", "", listOf(company, company), listOf(country, country), listOf(language, language), "Ok", "")

    private val moviesRepository: MoviesRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getPopularMovies(MovieSortType.NO) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        every { this@mockk.getPopularMovies(MovieSortType.ASC) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        every { this@mockk.getPopularMovies(MovieSortType.DESC) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))

        every { this@mockk.getNowPlayingMovies(MovieSortType.NO) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        every { this@mockk.getNowPlayingMovies(MovieSortType.ASC) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        every { this@mockk.getNowPlayingMovies(MovieSortType.DESC) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))

        every { this@mockk.getMovieDateRelease(-1) } returns Single.just(listOf(dateReleaseResultList.copy(iso = "-1")))
        every { this@mockk.getMovieDateRelease(0) } returns Single.just(listOf(dateReleaseResultList.copy(iso = "0")))
        every { this@mockk.getMovieDateRelease(1) } returns Single.just(listOf(dateReleaseResultList.copy(iso = "1")))

        every { this@mockk.getMovieDetails(-1) } returns Single.just(movieDetail.copy(id = -1))
        every { this@mockk.getMovieDetails(0) } returns Single.just(movieDetail.copy(id = 0))
        every { this@mockk.getMovieDetails(1) } returns Single.just(movieDetail.copy(id = 1))
    }

    @Test
    fun getPopularMovies_MovieSortTypeNo_false() {
        moviesRepository.getPopularMovies(MovieSortType.NO).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getPopularMovies_MovieSortTypeASC_false() {
        moviesRepository.getPopularMovies(MovieSortType.ASC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getPopularMovies_MovieSortTypeDESC_false() {
        moviesRepository.getPopularMovies(MovieSortType.DESC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getNowPlayingMovies_MovieSortTypeNO_false() {
        moviesRepository.getNowPlayingMovies(MovieSortType.NO).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getNowPlayingMovies_MovieSortTypeASC_false() {
        moviesRepository.getNowPlayingMovies(MovieSortType.ASC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getNowPlayingMovies_MovieSortTypeDESC_false() {
        moviesRepository.getNowPlayingMovies(MovieSortType.DESC).isEmpty.map {
            assertEquals(false, it)
        }
    }

    @Test
    fun getMovieDateRelease_NegativeId_True() {
        moviesRepository.getMovieDateRelease(-1).map {
            assertEquals(true, it.isNotEmpty() && it.first().iso == "-1")
            verify(exactly = 1) { moviesRepository.getMovieDateRelease(-1) }
        }
    }

    @Test
    fun getMovieDateRelease_ZeroId_True() {
        moviesRepository.getMovieDateRelease(0).map {
            assertEquals(true, it.isNotEmpty() && it.first().iso == "0")
            verify(exactly = 1) { moviesRepository.getMovieDateRelease(0) }
        }
    }

    @Test
    fun getMovieDateRelease_PositiveId_True() {
        moviesRepository.getMovieDateRelease(1).map {
            assertEquals(true, it.isNotEmpty() && it.first().iso == "1")
            verify(exactly = 1) { moviesRepository.getMovieDateRelease(1) }
        }
    }

    @Test
    fun getMovieDetail_NegativeId_True() {
        moviesRepository.getMovieDetails(-1).map {
            assertEquals(true, it.id == -1)
            verify(exactly = 1) { moviesRepository.getMovieDetails(-1) }
        }
    }

    @Test
    fun getMovieDetail_ZeroId_True() {
        moviesRepository.getMovieDetails(0).map {
            assertEquals(true, it.id == 0)
            verify(exactly = 1) { moviesRepository.getMovieDetails(0) }
        }
    }

    @Test
    fun getMovieDetail_PositiveId_True() {
        moviesRepository.getMovieDetails(1).map {
            assertEquals(true, it.id == 1)
            verify(exactly = 1) { moviesRepository.getMovieDetails(1) }
        }
    }
}