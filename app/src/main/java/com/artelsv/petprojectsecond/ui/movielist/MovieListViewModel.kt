package com.artelsv.petprojectsecond.ui.movielist

import androidx.lifecycle.LiveData
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

    private val mPopularMovies = MutableLiveData<List<Movie>?>(listOf())
    val popularMovies: LiveData<List<Movie>?> = mPopularMovies
    private val mNowPlayingMovies = MutableLiveData<List<Movie>?>(listOf())
    val nowPlayingMovies: LiveData<List<Movie>?> = mNowPlayingMovies

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)

    @ExperimentalCoroutinesApi
    val nowPlayingPagingData: Flowable<PagingData<Movie>> by lazy {
        getNowPlayingMoviesUseCase.invoke(MovieSortType.NO)
    }

    @ExperimentalCoroutinesApi
    val popularPagingData: Flowable<PagingData<Movie>> by lazy {
        getPopularMoviesUseCase.invoke(MovieSortType.NO)
    }

//    fun getNowPlayingMovies(sortType: MovieSortType) {
//        nowPlayingPagingData = getNowPlayingMoviesUseCase.invoke(sortType)
//    }
//
//    fun getPopularMovies(sortType: MovieSortType) {
//        popularPagingData = getPopularMoviesUseCase.invoke(MovieSortType.NO)
//    }

    init {
//        getMovies()
    }

//    fun getMovies(sortType: MovieSortType = MovieSortType.NO) {
//        val popularSingle = getPopularMoviesUseCase.invoke(MovieSortType.NO)
//        val newSingle = getNowPlayingMoviesUseCase.invoke(sortType)
//        val newSingle = Single.error<List<Movie>>(Throwable("a"))
//
//        val dis = Single.zip(newSingle, popularSingle, { new, popular ->
//            /**
//             * "Сортировка" по категориям
//             **/
//            val sorted = arrayListOf<Pair<MovieType, List<Movie>>>()
//
//            sorted.add(Pair(MovieType.NOW_PLAYING, new))
//            sorted.add(Pair(MovieType.POPULAR, popular))
//
//            return@zip sorted.toList()
//        })
//            .subscribeOn(Schedulers.io()) // TODO вот эти две штуки особо выжные, они отвечают за то, в каком потоке будет происходить обработка данных
//            .observeOn(AndroidSchedulers.mainThread())  // TODO обзательно посомтри вот это https://proandroiddev.com/understanding-rxjava-subscribeon-and-observeon-744b0c6a41ea
//            .doOnSubscribe {
//                error.postValue(false)
//            }
//            .subscribe({
//                /**
//                 * У меня тут 2 категории разные, и они в разных лайвдате лежат, не придумал способа лучше для разделения (опираюсь на тудушку выше, что работа должна проиходить здесь)
//                 **/
//                for (item in it) {
//                    when (item.first) {
//                        MovieType.POPULAR -> mPopularMovies.postValue(item.second)
//                        MovieType.NOW_PLAYING -> mNowPlayingMovies.postValue(item.second)
//                    }
//                }
//
//                loading.postValue(false)
//            }, {
//                error.postValue(true)
//                loading.postValue(false)
//            })
//
//        compositeDisposable.add(dis)
//    }
}