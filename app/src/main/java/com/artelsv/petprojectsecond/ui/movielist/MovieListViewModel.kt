package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import com.artelsv.petprojectsecond.domain.usecases.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : BaseViewModel() {

    val loadingPopular = MutableLiveData(false)
    val loadingNowPlaying = MutableLiveData(false)
    val error = MutableLiveData(false)

    private val mNowPlayingPagingLiveData = MutableLiveData<PagingData<Movie>>(null)
    val nowPlayingPagingLiveData: LiveData<PagingData<Movie>> = mNowPlayingPagingLiveData

    private val mPopularPagingLiveData = MutableLiveData<PagingData<Movie>>(null)
    val popularPagingLiveData: LiveData<PagingData<Movie>> = mPopularPagingLiveData

    init {
        setup()
    }

    private fun setup() {
        val nowPlayingPagingData: Flowable<PagingData<Movie>> by lazy {
            getNowPlayingMoviesUseCase.invoke(MovieSortType.NO).cachedIn(viewModelScope)
        }

        val popularPagingData: Flowable<PagingData<Movie>> by lazy {
            getPopularMoviesUseCase.invoke(MovieSortType.NO).cachedIn(viewModelScope)
        }

        compositeDisposable.add(
            nowPlayingPagingData.subscribe {
                mNowPlayingPagingLiveData.postValue(it)
            }
        )

        compositeDisposable.add(
            popularPagingData.subscribe {
                mPopularPagingLiveData.postValue(it)
            }
        )
    }

    fun progressCheck() = loadingPopular.value == true && loadingNowPlaying.value == true
}