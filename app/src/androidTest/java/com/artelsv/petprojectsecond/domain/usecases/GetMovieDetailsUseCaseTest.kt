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
class GetMovieDetailsUseCaseTest {
    private val genre = Genre(0, "Genre")
    private val company = Company(0, "Name", "", "Russia")
    private val country = Country("iso", "Name")
    private val language = Language("EnglishName", "iso", "Name")
    private val movieDetail = MovieDetail(false, "", 0, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000, listOf(genre, genre), "", "", listOf(company, company), listOf(country, country), listOf(language, language), "Ok", "")

    private val movieRepository = mockk<MoviesRepositoryImpl> {
        every { this@mockk.getMovieDetails(allAny()) } returns Single.just(movieDetail)
    }

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(movieRepository)

    @Test
    fun invoke() {
        for (i in 0..100) {
            getMovieDetailsUseCase.invoke(i).map {
                assertEquals(true, it != null)
            }
        }
    }
}