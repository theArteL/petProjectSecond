package com.artelsv.petprojectsecond.data.datasource.impl.movies

import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.datasource.MovieDataSource
import com.artelsv.petprojectsecond.data.mappers.MovieMapper
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import io.reactivex.Single
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val movieDao: MovieDao) : MovieDataSource {

    override fun getPopularMovies(page: Int): Single<List<Movie>> { // добавить логику для обработки страницы через локульную бд
        return movieDao.getAllMoviesByPage(MovieType.POPULAR, page)
            .map { it.map(MovieMapper::toMovie) }
    }

    override fun getNowPlayingMovies(page: Int): Single<List<Movie>> {
        return movieDao.getAllMoviesByPage(MovieType.NOW_PLAYING, page)
            .map { it.map(MovieMapper::toMovie) }
    }

    override fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>> {
        return Single.error(Throwable("Проверка ошибки"))
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetail> {
        return Single.error(Throwable("Проверка ошибки"))
    }

    override fun addMoviesToDb(data: List<Movie>, type: MovieType): List<Movie> {
        movieDao.addMovies(data.map { MovieMapper.toMovieEntity(it, type) })

        return data
    }

    override fun getMovieCredits(movieId: Int): Single<Credits> {
        return Single.error(Throwable("Проверка ошибки"))
    }

    override fun getMoviesByCredits(personId: Int): Single<List<Movie>> {
        return Single.error(Throwable("Проверка ошибки"))
    }

    override fun getPersonDetail(personId: Int): Single<PersonDetail> {
        return Single.error(Throwable("Проверка ошибки"))
    }

}