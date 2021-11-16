package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.movies.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUserUseCase: GetUserUseCase,
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

        getNowPlayingMoviesUseCase(MovieSortType.NO)
            .cachedIn(viewModelScope)
            .subscribe({
                mNowPlayingPagingLiveData.postValue(it)
            }, {

            }).addToComposite()

        getPopularMoviesUseCase(MovieSortType.NO)
            .cachedIn(viewModelScope)
            .subscribe({
                mPopularPagingLiveData.postValue(it)
            }, {

            }).addToComposite()
    }

    private fun getUser() {
        getUserUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                getUserUseCase.syncLocalUserLists(it.id)
                user.postValue(it)
            }
            .subscribe({

            }, {
//                Timber.e(it)
            })
            .addToComposite()
    }

    fun progressCheck() = loadingPopular.value == true && loadingNowPlaying.value == true

    override fun onCleared() {
        super.onCleared()

        getUserUseCase.dispose()
    }
}