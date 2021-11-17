package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.model.movie.DateRelease
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetMovieDateReleaseUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.movies.GetMovieDateReleaseUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDateReleaseUseCaseTest {

    private val dateRelease = DateRelease("cert", "US", "11.11.2011", 0)
    private val dateReleaseResultList = DateReleaseResult("US", listOf(dateRelease, dateRelease))

    private val movieRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getMovieDateRelease(allAny()) } returns Single.just(listOf(dateReleaseResultList, dateReleaseResultList))
    }

    private val getMovieDateReleaseUseCase: GetMovieDateReleaseUseCase = GetMovieDateReleaseUseCaseImpl(movieRepository)

    @Test
    fun invoke_NegativeMovieIdWithRuIso_True() {
        getMovieDateReleaseUseCase(-100, "RU").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }
    }

    @Test
    fun invoke_ZeroMovieIdWithNotCorrectIso_True() {
        getMovieDateReleaseUseCase(0, "ADIAS").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }
    }

    @Test
    fun invoke_PositiveMovieIdWithUsIso_True() {
        getMovieDateReleaseUseCase(100, "US").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }
    }

    @Test
    fun invoke_MaxIntegerMovieIdWithEmptyIso_True() {
        getMovieDateReleaseUseCase(Integer.MAX_VALUE, "").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }
    }

    @Test
    fun invoke_MinIntegerMovieIdWithEmojiIso_True() {
        getMovieDateReleaseUseCase(Integer.MIN_VALUE, "\uD83D\uDE0B\uD83D\uDE0B\uD83D\uDE0B").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }
    }
}