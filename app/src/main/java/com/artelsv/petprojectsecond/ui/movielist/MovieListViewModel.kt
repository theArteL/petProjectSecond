package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.movies.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.SyncLocalUserListsUseCase
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val syncLocalUserListsUseCase: SyncLocalUserListsUseCase,
    private val router: Router,
) : BaseViewModel() {

    val user = MutableLiveData<User>(null)

    val loadingPopular = MutableLiveData(false)
    val loadingNowPlaying = MutableLiveData(false)
    val error = MutableLiveData(false)

    private val mNowPlayingPagingLiveData = MutableLiveData<PagingData<Movie>>(null)
    val nowPlayingPagingLiveData: LiveData<PagingData<Movie>> = mNowPlayingPagingLiveData

    private val mPopularPagingLiveData = MutableLiveData<PagingData<Movie>>(null)
    val popularPagingLiveData: LiveData<PagingData<Movie>> = mPopularPagingLiveData

    val nowPlayingAdapter: MovieAdapter = MovieAdapter {
        it?.let {
            navigateToMovieDetail(it.id)
        }
    }.apply {
        withLoadStateHeaderAndFooter(header = MovieLoaderStateAdapter(), footer = MovieLoaderStateAdapter())
        addLoadStateListener { state ->
            loadingNowPlaying.postValue(state.refresh != LoadState.Loading)
        }
    }

    val popularAdapter: MovieAdapter = MovieAdapter {
        it?.let {
            navigateToMovieDetail(it.id)
        }
    }.apply {
        withLoadStateHeaderAndFooter(header = MovieLoaderStateAdapter(), footer = MovieLoaderStateAdapter())
        addLoadStateListener { state ->
            loadingPopular.postValue(state.refresh != LoadState.Loading)
        }
    }

    init {
        setup()
    }

    fun processNowPlayingLoading() {
        loadingNowPlaying.postValue(true)
    }

    fun processPopularLoading() {
        loadingPopular.postValue(true)
    }

    fun navigateToProfile() {
        router.navigateTo(Screens.profile())
    }

    fun navigateToMovieDetail(id: Int) {
        router.navigateTo(Screens.movieDetail(id))
    }

    private fun setup() {
        getUser()

        Flowable.zip(getNowPlayingMoviesUseCase(MovieSortType.NO), getPopularMoviesUseCase(MovieSortType.NO), { nowPlaying, popular ->
            nowPlayingAdapter.run { viewModelScope.launch(Dispatchers.Main) { submitData(nowPlaying) } }
            popularAdapter.run { viewModelScope.launch(Dispatchers.Main) { submitData(popular) } }
//            mPopularPagingLiveData.postValue(popular)
        }).subscribe({

        }, {

        }).addToComposite()
    }

    private fun getUser() {
        getUserUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                syncLocalUserListsUseCase(it.id).addToComposite()
                user.postValue(it)
            }, {
                Timber.e(it)
            })
            .addToComposite()
    }

    fun progressCheck() = loadingPopular.value == true && loadingNowPlaying.value == true
}