package com.artelsv.petprojectsecond.data.datasource

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.artelsv.petprojectsecond.data.mappers.MovieMapper
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.MovieResponse
import com.artelsv.petprojectsecond.domain.model.Movie
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviePagingSource @AssistedInject constructor(
    private val moviesService: MoviesService,
    @Assisted("debug") private val debug: Boolean
) : RxPagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val position = params.key ?: INITIAL_PAGE_NUMBER

        val response = moviesService.getNowPlayingMovies(position)

        return response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
            Log.e("aa", "loadres")
            toLoadResult(it, position)
        }.onErrorReturn {
            Log.e("osad", "osibka ${it.message}")
            LoadResult.Error(it)
        }
    }

    private fun toLoadResult(data: MovieListResponse, position: Int): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = data.results.map(MovieMapper::toMovie),
            prevKey = if (position == 1) null else position - 1 ,
            nextKey = if (position < data.totalPages!!)
                data.page?.plus(1) else null
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("debug") debug: Boolean): MoviePagingSource
    }

    companion object {

        private const val INITIAL_PAGE_NUMBER = 1
    }
}