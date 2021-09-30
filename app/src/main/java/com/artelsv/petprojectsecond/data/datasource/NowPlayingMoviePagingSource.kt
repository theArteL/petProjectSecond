package com.artelsv.petprojectsecond.data.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NowPlayingMoviePagingSource @AssistedInject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    @Assisted("sort") private val sort: MovieSortType
) : RxPagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val page = params.key ?: INITIAL_PAGE_NUMBER

        val response = movieRemoteDataSource.getNowPlayingMovies(page).onErrorResumeNext { movieLocalDataSource.getNowPlayingMovies(page) }

        return response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
            toLoadResult(it, page, sort)
        }.onErrorReturn {
            LoadResult.Error(it)
        }
    }

    private fun toLoadResult(data: List<Movie>, position: Int, movieSortType: MovieSortType): LoadResult<Int, Movie> {
        return when(movieSortType) {
            MovieSortType.NO -> LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position < MAX_PAGE_NUMBER) position + 1 else null
            )
            MovieSortType.ASC -> LoadResult.Page(
                data = data.sortedBy { it.voteAverage },
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position < MAX_PAGE_NUMBER) position + 1 else null
            )
            MovieSortType.DESC -> LoadResult.Page(
                data = data.sortedByDescending { it.voteAverage },
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position < MAX_PAGE_NUMBER) position + 1 else null
            )
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("sort") sort: MovieSortType): NowPlayingMoviePagingSource
    }

    companion object {

        private const val INITIAL_PAGE_NUMBER = 1
        private const val MAX_PAGE_NUMBER = 500
    }
}