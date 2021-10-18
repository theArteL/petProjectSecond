package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    val user = MutableLiveData<User>(null)

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
        getUser()

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

    private fun getUser() {
        compositeDisposable.add(getUserUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                user.postValue(it)
            }
            .subscribe({

            }, {
                Timber.e(it)
            })
        )
    }

    fun progressCheck() = loadingPopular.value == true && loadingNowPlaying.value == true
}