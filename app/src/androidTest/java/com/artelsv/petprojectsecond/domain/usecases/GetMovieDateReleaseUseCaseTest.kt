package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.model.*
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
        for (i in 0..100) {
            getMovieDateReleaseUseCase.invoke(i, if (i % 2 == 0) "US" else "RU").map {
                assertEquals(true, it.iso.isNotEmpty() && it.releaseDates.isNotEmpty())
            }
        }
    }
}