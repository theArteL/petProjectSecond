package com.artelsv.petprojectsecond.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.getOrAwaitValue
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieListViewModelTest : TestCase() {
    private lateinit var movieListViewModel: MovieListViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        movieListViewModel = MovieListViewModel(mockk(), mockk())
    }

    @Test
    fun progressCheckTrue() {
        movieListViewModel.loadingPopular.postValue(true)
        movieListViewModel.loadingNowPlaying.postValue(true)

        assertEquals(true, movieListViewModel.loadingPopular.getOrAwaitValue())
        assertEquals(true, movieListViewModel.loadingNowPlaying.getOrAwaitValue())

        assertEquals(true, movieListViewModel.progressCheck())
    }

    @Test
    fun progressCheckFalse() {
        movieListViewModel.loadingPopular.postValue(false)
        movieListViewModel.loadingNowPlaying.postValue(true)

        assertEquals(false, movieListViewModel.progressCheck())

        movieListViewModel.loadingPopular.postValue(true)
        movieListViewModel.loadingNowPlaying.postValue(false)

        assertEquals(false, movieListViewModel.progressCheck())

        movieListViewModel.loadingPopular.postValue(false)
        movieListViewModel.loadingNowPlaying.postValue(false)

        assertEquals(false, movieListViewModel.progressCheck())

        movieListViewModel.loadingPopular.postValue(null)
        movieListViewModel.loadingNowPlaying.postValue(null)

        assertEquals(false, movieListViewModel.progressCheck())
    }
}