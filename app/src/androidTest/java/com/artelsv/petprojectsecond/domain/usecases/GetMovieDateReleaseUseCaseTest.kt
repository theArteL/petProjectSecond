package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.usecases.impl.GetMovieDateReleaseUseCaseImpl
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
    fun invoke() {
        getMovieDateReleaseUseCase.invoke(-100, "RU").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }

        getMovieDateReleaseUseCase.invoke(0, "ADIAS").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }

        getMovieDateReleaseUseCase.invoke(100, "US").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }

        getMovieDateReleaseUseCase.invoke(Integer.MAX_VALUE, "").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }

        getMovieDateReleaseUseCase.invoke(Integer.MIN_VALUE, "\uD83D\uDE0B\uD83D\uDE0B\uD83D\uDE0B").map {
            assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
        }
    }
}