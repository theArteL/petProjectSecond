package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.mappers.MovieDetailMapper
import com.artelsv.petprojectsecond.data.mappers.MovieMapper
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.model.MovieType
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(val movieDao: MovieDao, private val moviesService: MoviesService) :
    MoviesRepository {

    override fun getPopularMovies(): Single<List<Movie>> {
        return moviesService.getPopularMovies().map {
            addMoviesToDb(it, MovieType.POPULAR)
        }
    }

    override fun getNowPlayingMovies(): Single<List<Movie>> {
        return moviesService.getNowPlayingMovies().map {
            addMoviesToDb(it, MovieType.NOW_PLAYING)
        }
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetail> {
        return moviesService.getMovieDetail(movieId).map(MovieDetailMapper::toMovieDetail)
    }

    private fun addMoviesToDb(data: MovieListResponse, type: MovieType) : List<Movie> {
        movieDao.addMovies(data.results.map { MovieMapper.toMovieEntity(it, type) })

        return data.results.map { MovieMapper.toMovie(it, type) }
    }
}