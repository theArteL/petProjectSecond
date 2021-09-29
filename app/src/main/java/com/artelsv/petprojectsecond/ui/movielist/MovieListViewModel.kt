package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.artelsv.petprojectsecond.data.datasource.MoviePagingSource
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import com.artelsv.petprojectsecond.domain.model.MovieType
import com.artelsv.petprojectsecond.domain.usecases.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCase
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val moviePagingSource: MoviePagingSource.Factory
) : BaseViewModel() {

    private val mPopularMovies = MutableLiveData<List<Movie>?>(listOf())
    val popularMovies: LiveData<List<Movie>?> = mPopularMovies
    private val mNowPlayingMovies = MutableLiveData<List<Movie>?>(listOf())
    val nowPlayingMovies: LiveData<List<Movie>?> = mNowPlayingMovies

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)

    @ExperimentalCoroutinesApi
    val pagingData: Flowable<PagingData<Movie>> = Pager(
        config = PagingConfig(20),
        pagingSourceFactory = { moviePagingSource.create(true) }
    ).flowable

    init {
//        getMovies()
    }

    fun getMovies(sortType: MovieSortType = MovieSortType.NO) {
        val popularSingle = getPopularMoviesUseCase.invoke(MovieSortType.NO)
        val newSingle = getNowPlayingMoviesUseCase.invoke(sortType)
//        val newSingle = Single.error<List<Movie>>(Throwable("a"))

        val dis = Single.zip(newSingle, popularSingle, { new, popular ->
            /**
             * "Сортировка" по категориям
             **/
            val sorted = arrayListOf<Pair<MovieType, List<Movie>>>()

            sorted.add(Pair(MovieType.NOW_PLAYING, new))
            sorted.add(Pair(MovieType.POPULAR, popular))

            return@zip sorted.toList()
        })
            .subscribeOn(Schedulers.io()) // TODO вот эти две штуки особо выжные, они отвечают за то, в каком потоке будет происходить обработка данных
            .observeOn(AndroidSchedulers.mainThread())  // TODO обзательно посомтри вот это https://proandroiddev.com/understanding-rxjava-subscribeon-and-observeon-744b0c6a41ea
            .doOnSubscribe {
                error.postValue(false)
            }
            .subscribe({
                /**
                 * У меня тут 2 категории разные, и они в разных лайвдате лежат, не придумал способа лучше для разделения (опираюсь на тудушку выше, что работа должна проиходить здесь)
                 **/
                for (item in it) {
                    when (item.first) {
                        MovieType.POPULAR -> mPopularMovies.postValue(item.second)
                        MovieType.NOW_PLAYING -> mNowPlayingMovies.postValue(item.second)
                    }
                }

                loading.postValue(false)
            }, {
                error.postValue(true)
                loading.postValue(false)
            })

        compositeDisposable.add(dis)
    }
}