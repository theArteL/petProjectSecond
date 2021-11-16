package com.artelsv.petprojectsecond.ui.moviedetail

import android.content.Context
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.model.movie.*
import com.artelsv.petprojectsecond.domain.usecases.movies.GetMovieDateReleaseUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetMovieDateReleaseUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail.GetMovieDetailsUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.user.GetUserUseCaseImpl
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

@RunWith(AndroidJUnit4::class)
class MovieDetailViewModelTest : TestCase() {
    private val dateRelease = DateRelease("cert", "iso", "2021-08-11T00:00:00.000Z", 0)
    private val dateReleaseResultList = DateReleaseResult("iso", listOf(dateRelease, dateRelease))

    private val genre = Genre(0, "Genre")
    private val company = Company(0, "Name", "", "Russia")
    private val country = Country("iso", "Name")
    private val language = Language("EnglishName", "iso", "Name")
    private val movieDetail = MovieDetail(false, "", 0, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "2021-08-11T00:00:00.000Z", 0, 0, "MovieTitle", false, 5.0f, 1000, listOf(genre, genre), "", "", listOf(company, company), listOf(country, country), listOf(language, language), "Ok", "")

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk<GetMovieDetailsUseCaseImpl> {
        every { this@mockk(allAny()) } returns Single.just(movieDetail)
    }

    private val getMovieDateReleaseUseCase: GetMovieDateReleaseUseCase = mockk<GetMovieDateReleaseUseCaseImpl> {
        every { this@mockk(allAny(), allAny()) } returns Single.just(dateReleaseResultList)
    }

    private val getUserUseCase: GetUserUseCase = mockk<GetUserUseCaseImpl> {

    }

    private val res = mockk<Resources> {
        every { this@mockk.getString(R.string.movie_detail_separator) } returns "value"
        every { this@mockk.getString(allAny()) } returns "value"
        every { this@mockk.getString(allAny(), allAny()) } returns "value"
        every { this@mockk.getString(allAny(), allAny(), allAny()) } returns "value"
    }

    @Before
    fun setup() {
        movieDetailViewModel = MovieDetailViewModel(context, getMovieDetailsUseCase, getMovieDateReleaseUseCase, getUserUseCase)
    }

    @Test
    fun getMovieDetail_NegativeId_false() {
        movieDetailViewModel.getMovieDetail(-1)

        assertEquals(false, movieDetailViewModel.dateRelease.getOrAwaitValue() != null)
        assertEquals(false, movieDetailViewModel.movie.getOrAwaitValue() != null)
    }

    @Test
    fun getMovieDetail_ZeroId_false() {
        movieDetailViewModel.getMovieDetail(0)

        assertEquals(false, movieDetailViewModel.dateRelease.getOrAwaitValue() != null)
        assertEquals(false, movieDetailViewModel.movie.getOrAwaitValue() != null)
    }

    @Test
    fun getMovieDetail_PositiveId_false() {
        movieDetailViewModel.getMovieDetail(1)

        assertEquals(false, movieDetailViewModel.dateRelease.getOrAwaitValue() != null)
        assertEquals(false, movieDetailViewModel.movie.getOrAwaitValue() != null)
    }

    @Test
    fun getMovieDetail_NegativeId_UIVarsNotNull() {
        movieDetailViewModel.getMovieDetail(-1)

        if (movieDetailViewModel.dateRelease.getOrAwaitValue() != null && movieDetailViewModel.movie.getOrAwaitValue() != null) {
            assertEquals(false, movieDetailViewModel.imageUrl.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.voteAsString.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.voteColor.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.movieName.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.getGenresAsString(res, movieDetail).isEmpty())
        }
    }

    @Test
    fun getMovieDetail_ZeroId_UIVarsNotNull() {
        movieDetailViewModel.getMovieDetail(0)

        if (movieDetailViewModel.dateRelease.getOrAwaitValue() != null && movieDetailViewModel.movie.getOrAwaitValue() != null) {
            assertEquals(false, movieDetailViewModel.imageUrl.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.voteAsString.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.voteColor.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.movieName.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.getGenresAsString(res, movieDetail).isEmpty())
        }

    }

    @Test
    fun getMovieDetail_PositiveId_UIVarsNotNull() {
        movieDetailViewModel.getMovieDetail(1)

        if (movieDetailViewModel.dateRelease.getOrAwaitValue() != null && movieDetailViewModel.movie.getOrAwaitValue() != null) {
            assertEquals(false, movieDetailViewModel.imageUrl.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.voteAsString.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.voteColor.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.movieName.getOrAwaitValue() != null)
            assertEquals(false, movieDetailViewModel.getGenresAsString(res, movieDetail).isEmpty())
        }

    }
}