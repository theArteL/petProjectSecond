package com.artelsv.petprojectsecond.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.artelsv.petprojectsecond.data.database.dao.MovieDao.Companion.PAGE_SIZE
import com.artelsv.petprojectsecond.data.datasource.MovieDataSource
import com.artelsv.petprojectsecond.data.datasource.impl.movies.NowPlayingMoviePagingSource
import com.artelsv.petprojectsecond.data.datasource.impl.movies.PopularMoviePagingSource
import com.artelsv.petprojectsecond.data.datasource.UserLocalDataSource
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDataSource,
    private val remoteDataSource: MovieDataSource,
    // только для получения актуальной инфы из бд
    private val userLocalDataSource: UserLocalDataSource,
    private val nowPlayingMoviePagingSource: NowPlayingMoviePagingSource.Factory,
    private val popularMoviePagingSource: PopularMoviePagingSource.Factory,
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
            .map {
                val local = userLocalDataSource.get(it.id)
                if (local != null) it.copy(rating = local.rating, favorite = local.favorite) else it
            }
            .onErrorResumeNext {
                localDataSource.getMovieDetails(movieId)
            }
    }

    override fun getMovieCredits(movieId: Int): Single<Credits> {
        return remoteDataSource.getMovieCredits(movieId)
    }

    override fun getMoviesByCredits(personId: Int): Single<List<Movie>> {
        return remoteDataSource.getMoviesByCredits(personId)
    }

    override fun getPersonDetail(personId: Int): Single<PersonDetail> {
        return remoteDataSource.getPersonDetail(personId)
    }
}