package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.mappers.MovieMapper
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.data.network.model.MovieResponse
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.MovieType
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(val movieDao: MovieDao, private val moviesService: MoviesService) :
    MoviesRepository {

    /**
     * TODO попробуй использовтаь [kotlin.Result]
     * вот тут можешь глянуть как https://blog.mindorks.com/using-retrofit-with-kotlin-coroutines-in-android
     */
    override fun getPopularMovies(): Single<MovieResponse> {
        return moviesService.getPopularMovies().map {
            addMoviesToDb(it, MovieType.POPULAR)
        }
    }

    override fun getNowPlayingMovies(): Single<MovieResponse> {
        return moviesService.getNowPlayingMovies().map {
            addMoviesToDb(it, MovieType.NOW_PLAYING)


        }
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetailResponse> {
        return moviesService.getMovieDetail(movieId)
    }

    private fun addMoviesToDb(data: MovieResponse, type: MovieType) : MovieResponse {
        val mapper = MovieMapper(type)

        movieDao.addMovies(mapper.toListMovieEntity(data.results))

        return data
    }
}