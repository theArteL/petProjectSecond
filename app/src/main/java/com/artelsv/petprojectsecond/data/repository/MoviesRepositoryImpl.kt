package com.artelsv.petprojectsecond.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.artelsv.petprojectsecond.data.database.dao.MovieDao.Companion.PAGE_SIZE
import com.artelsv.petprojectsecond.data.datasource.MovieDataSource
import com.artelsv.petprojectsecond.data.datasource.NowPlayingMoviePagingSource
import com.artelsv.petprojectsecond.data.datasource.PopularMoviePagingSource
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDataSource,
    private val remoteDataSource: MovieDataSource,
    private val nowPlayingMoviePagingSource: NowPlayingMoviePagingSource.Factory,
    private val popularMoviePagingSource: PopularMoviePagingSource.Factory
) : MoviesRepository {

    override fun getPopularMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { popularMoviePagingSource.create() }
        ).flowable
    }

    override fun getNowPlayingMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { nowPlayingMoviePagingSource.create() }
        ).flowable
    }

    override fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>> {
        return remoteDataSource.getMovieDateRelease(movieId)
            .onErrorResumeNext {
                localDataSource.getMovieDateRelease(movieId)
            }
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetail> {
        return remoteDataSource.getMovieDetails(movieId)
            .onErrorResumeNext {
                localDataSource.getMovieDetails(movieId)
            }
    }
}