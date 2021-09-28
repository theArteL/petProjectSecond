package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.MovieDataSource
import com.artelsv.petprojectsecond.data.mappers.MovieDateResultMapper
import com.artelsv.petprojectsecond.data.mappers.MovieDetailMapper
import com.artelsv.petprojectsecond.data.mappers.MovieMapper
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.model.MovieType
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MovieDataSource,
    private val remoteDataSource: MovieDataSource
) : MoviesRepository {

    override fun getPopularMovies(): Single<List<Movie>> {
        return remoteDataSource.getPopularMovies().map {
            localDataSource.addMoviesToDb(it, MovieType.POPULAR)
        }.onErrorResumeNext {
            localDataSource.getPopularMovies()
        }
    }

    override fun getNowPlayingMovies(): Single<List<Movie>> {
        return remoteDataSource.getNowPlayingMovies().map {
            localDataSource.addMoviesToDb(it, MovieType.NOW_PLAYING)
        }.onErrorResumeNext {
            localDataSource.getNowPlayingMovies()
        }
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