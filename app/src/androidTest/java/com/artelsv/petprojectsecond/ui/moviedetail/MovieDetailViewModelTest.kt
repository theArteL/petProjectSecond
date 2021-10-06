package com.artelsv.petprojectsecond.ui.moviedetail

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDateReleaseUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDateReleaseUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCaseImpl
import com.artelsv.petprojectsecond.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

@RunWith(AndroidJUnit4::class)
class MovieDetailViewModelTest : TestCase() {
    private val dateRelease = DateRelease("cert", "iso", "2021-08-11T00:00:00.000Z", 0)
    private val dateReleaseResultList = DateReleaseResult("iso", listOf(dateRelease, dateRelease))

    private val genre = Genre(0, "Genre")
    private val company = Company(0, "Name", "", "Russia")
    private val country = Country("iso", "Name")
    private val language = Language("EnglishName", "iso", "Name")
    private val movieDetail = MovieDetail(false, "", 0, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "2021-08-11T00:00:00.000Z", 0, 0, "MovieTitle", false, 5.0, 1000, listOf(genre, genre), "", "", listOf(company, company), listOf(country, country), listOf(language, language), "Ok", "")

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk<GetMovieDetailsUseCaseImpl> {
        every { this@mockk.invoke(allAny()) } returns Single.just(movieDetail)
    }

    private val getMovieDateReleaseUseCase: GetMovieDateReleaseUseCase = mockk<GetMovieDateReleaseUseCaseImpl> {
        every { this@mockk.invoke(allAny(), allAny()) } returns Single.just(dateReleaseResultList)
    }

    private val res = mockk<Resources> {
        every { this@mockk.getString(R.string.movie_detail_separator) } returns "value"
        every { this@mockk.getString(allAny()) } returns "value"
        every { this@mockk.getString(allAny(), allAny()) } returns "value"
        every { this@mockk.getString(allAny(), allAny(), allAny()) } returns "value"
    }

    @Before
    fun setup() {
        movieDetailViewModel = MovieDetailViewModel(getMovieDetailsUseCase, getMovieDateReleaseUseCase)
    }

    @Test
    fun setMovieValue() {
        movieDetailViewModel.setMovieValue(-1)

        assertEquals(false, movieDetailViewModel.dateRelease.getOrAwaitValue() != null)
        assertEquals(false, movieDetailViewModel.movie.getOrAwaitValue() != null)

        movieDetailViewModel.setMovieValue(0)

        assertEquals(false, movieDetailViewModel.dateRelease.getOrAwaitValue() != null)
        assertEquals(false, movieDetailViewModel.movie.getOrAwaitValue() != null)

        movieDetailViewModel.setMovieValue(1)

        assertEquals(false, movieDetailViewModel.dateRelease.getOrAwaitValue() != null)
        assertEquals(false, movieDetailViewModel.movie.getOrAwaitValue() != null)
    }

    @Test
    fun getImageUrl() {
        for (i in -100..100) {
            assertEquals(false, movieDetailViewModel.getImageUrl(movieDetail.copy(id = i, backdropPath = buildString { repeat(abs(i)) { this.append(1) } })).isEmpty())
        }
    }

    @Test
    fun getVoteAsString() {
        for (i in 1..200) {
            assertEquals(false, movieDetailViewModel.getVoteAsString(movieDetail.copy(id = i)).isEmpty())
        }
    }

    @Test
    fun getVoteColor() {
        for (i in -200..200) {

            assertEquals(when {
                i % 2 == 0 -> R.color.red
                i % 3 == 0 -> R.color.yellow
                i % 7 == 0 -> R.color.green
                else -> R.color.red
            }, movieDetailViewModel.getVoteColor(movieDetail.copy(voteAverage = when {
                i % 2 == 0 -> 4.0
                i % 3 == 0 -> 6.0
                i % 7 == 0 -> 8.0
                else -> 0.0
            })))
        }
    }

    @Test
    fun getMovieName() {
        movieDetailViewModel.setMovieValue(0)

        for (i in 1..200) {
            assertEquals(false, movieDetailViewModel.getMovieName(res).isEmpty())
        }
    }

    @Test
    fun getGenresAsString() {
        movieDetailViewModel.setMovieValue(1)

        if (movieDetailViewModel.dateRelease.getOrAwaitValue() != null && movieDetailViewModel.movie.getOrAwaitValue() != null) {
            for (i in 1..200) {
                assertEquals(false, movieDetailViewModel.getGenresAsString(res).isEmpty())
            }
        }
    }
}