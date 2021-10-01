package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import com.artelsv.petprojectsecond.domain.usecases.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : BaseViewModel() {

    val loadingPopular = MutableLiveData(false)
    val loadingNowPlaying = MutableLiveData(false)
    val error = MutableLiveData(false)

    @ExperimentalCoroutinesApi
    val nowPlayingPagingData: Flowable<PagingData<Movie>> by lazy {
        getNowPlayingMoviesUseCase.invoke(MovieSortType.NO)
    }

    @ExperimentalCoroutinesApi
    val popularPagingData: Flowable<PagingData<Movie>> by lazy {
        getPopularMoviesUseCase.invoke(MovieSortType.NO)
    }

    fun progressCheck() = loadingPopular.value == true && loadingNowPlaying.value == true
}