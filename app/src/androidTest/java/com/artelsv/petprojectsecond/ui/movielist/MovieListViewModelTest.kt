package com.artelsv.petprojectsecond.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.domain.model.*
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import com.artelsv.petprojectsecond.domain.usecases.movies.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetNowPlayingMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetPopularMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.user.GetUserUseCaseImpl
import com.artelsv.petprojectsecond.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieListViewModelTest : TestCase() {
    private lateinit var movieListViewModel: MovieListViewModel

    private val movieNowPlaying = Movie(false, "", 1000, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000)
    private val moviePopular = Movie(false, "", 1000, listOf(1, 2), 0, "Russian", "MovieTitle", "MovieOverview", 5.0, "", "11.11.2011", 0, 0, "MovieTitle", false, 5.0, 1000, movieType = MovieType.POPULAR)

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk<GetPopularMoviesUseCaseImpl> {
        every { this@mockk(allAny()) } returns Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
    }

    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase = mockk<GetNowPlayingMoviesUseCaseImpl> {
        every { this@mockk(allAny()) } returns Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
    }

    private val getUserUseCase: GetUserUseCase = mockk<GetUserUseCaseImpl> {

    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        movieListViewModel = MovieListViewModel(getPopularMoviesUseCase, getNowPlayingMoviesUseCase, getUserUseCase)
    }

    @Test
    fun progressCheck_LoadingPopularTrueAndLoadingNowPlayingTrue_True() {
        movieListViewModel.loadingPopular.postValue(true)
        movieListViewModel.loadingNowPlaying.postValue(true)

        assertEquals(true, movieListViewModel.loadingPopular.getOrAwaitValue())
        assertEquals(true, movieListViewModel.loadingNowPlaying.getOrAwaitValue())

        assertEquals(true, movieListViewModel.progressCheck())
    }

    @Test
    fun progressCheck_LoadingPopularFalseAndLoadingNowPlayingTrue_False() {
        movieListViewModel.loadingPopular.postValue(false)
        movieListViewModel.loadingNowPlaying.postValue(true)

        assertEquals(false, movieListViewModel.progressCheck())
    }

    @Test
    fun progressCheck_LoadingPopularTrueAndLoadingNowPlayingFalse_False() {
        movieListViewModel.loadingPopular.postValue(true)
        movieListViewModel.loadingNowPlaying.postValue(false)

        assertEquals(false, movieListViewModel.progressCheck())
    }

    @Test
    fun progressCheck_LoadingPopularFalseAndLoadingNowPlayingFalse_False() {
        movieListViewModel.loadingPopular.postValue(false)
        movieListViewModel.loadingNowPlaying.postValue(false)

        assertEquals(false, movieListViewModel.progressCheck())
    }

    @Test
    fun progressCheck_LoadingPopularNullAndLoadingNowPlayingNull_False() {
        movieListViewModel.loadingPopular.postValue(null)
        movieListViewModel.loadingNowPlaying.postValue(null)

        assertEquals(false, movieListViewModel.progressCheck())
    }
}