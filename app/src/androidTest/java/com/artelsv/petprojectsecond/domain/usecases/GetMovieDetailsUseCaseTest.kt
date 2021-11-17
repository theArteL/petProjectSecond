package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.data.repository.UserRepositoryImpl
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.model.movie.*
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail.GetMovieDetailsUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieDetailsUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsUseCaseTest {
    private val genre = Genre(0, "Genre")
    private val company = Company(0, "Name", "", "Russia")
    private val country = Country("iso", "Name")
    private val language = Language("EnglishName", "iso", "Name")
    private val movieDetail = MovieDetail(false, "", 0, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0f, 1000, listOf(genre, genre), "", "", listOf(company, company), listOf(country, country), listOf(language, language), "Ok", "")

    private val movieRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getMovieDetails(-100) } returns Single.just(movieDetail.copy(id = -100))
        every { this@mockk.getMovieDetails(0) } returns Single.just(movieDetail.copy(id = 0))
        every { this@mockk.getMovieDetails(100) } returns Single.just(movieDetail.copy(id = 100))
        every { this@mockk.getMovieDetails(Integer.MAX_VALUE) } returns Single.just(movieDetail.copy(id = Integer.MAX_VALUE))
        every { this@mockk.getMovieDetails(Integer.MIN_VALUE) } returns Single.just(movieDetail.copy(id = Integer.MIN_VALUE))
    }

    private val userRepository = mockk<UserRepositoryImpl> {

    }

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(movieRepository, userRepository)

    @Test
    fun invoke_NegativeMovieId_True() {
        getMovieDetailsUseCase(-100).map {
            assertEquals(true, it.id == -100)
        }
    }

    @Test
    fun invoke_PositiveMovieId_True() {
        getMovieDetailsUseCase(100).map {
            assertEquals(true, it.id == 100)
        }
    }

    @Test
    fun invoke_ZeroMovieId_True() {
        getMovieDetailsUseCase(0).map {
            assertEquals(true, it.id == 0)
        }
    }

    @Test
    fun invoke_MaxIntegerMovieId_True() {
        getMovieDetailsUseCase(Integer.MAX_VALUE).map {
            assertEquals(true, it.id == Integer.MAX_VALUE)
        }
    }

    @Test
    fun invoke_MinIntegerMovieId_True() {
        getMovieDetailsUseCase(Integer.MIN_VALUE).map {
            assertEquals(true, it.id == Integer.MIN_VALUE)
        }
    }
}