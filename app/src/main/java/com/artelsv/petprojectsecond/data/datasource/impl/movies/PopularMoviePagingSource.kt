package com.artelsv.petprojectsecond.data.datasource.impl.movies

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.artelsv.petprojectsecond.data.datasource.MovieDataSource
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

class PopularMoviePagingSource @AssistedInject constructor(
    @Named("movieLocalDataSource") private val movieLocalDataSource: MovieDataSource,
    @Named("movieRemoteDataSource") private val movieRemoteDataSource: MovieDataSource
) : RxPagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val page = params.key ?: INITIAL_PAGE_NUMBER

        val response = movieRemoteDataSource.getPopularMovies(page).map {
            movieLocalDataSource.addMoviesToDb(it, MovieType.NOW_PLAYING)
        }.onErrorResumeNext { movieLocalDataSource.getPopularMovies(page) }

        return response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
            toLoadResult(it, page)
        }.onErrorReturn {
            LoadResult.Error(it)
        }
    }

    private fun toLoadResult(data: List<Movie>, position: Int): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position < MAX_PAGE_NUMBER) position + 1 else null
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(): PopularMoviePagingSource
    }

    companion object {

        private const val INITIAL_PAGE_NUMBER = 1
        private const val MAX_PAGE_NUMBER = 500
    }
}