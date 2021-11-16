package com.artelsv.petprojectsecond.data.datasource.impl.movies

import com.artelsv.petprojectsecond.data.datasource.MovieDataSource
import com.artelsv.petprojectsecond.data.mappers.CreditsMapper
import com.artelsv.petprojectsecond.data.mappers.MovieDateResultMapper
import com.artelsv.petprojectsecond.data.mappers.MovieDetailMapper
import com.artelsv.petprojectsecond.data.mappers.MovieMapper
import com.artelsv.petprojectsecond.data.mappers.persondetail.PersonDetailMapper
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.network.model.persondetail.toModel
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val moviesService: MoviesService) :
    MovieDataSource {

    override fun getPopularMovies(page: Int): Single<List<Movie>> =
        moviesService.getPopularMovies(page).map { it.results.map(MovieMapper::toMovie) }

    override fun getNowPlayingMovies(page: Int): Single<List<Movie>> =
        moviesService.getNowPlayingMovies(page).map { it.results.map(MovieMapper::toMovie) }

    override fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>> =
        moviesService.getMovieReleaseDates(movieId)
            .map { it.results.map(MovieDateResultMapper::toDateReleaseResult) }

    override fun getMovieDetails(movieId: Int): Single<MovieDetail> =
        moviesService.getMovieDetail(movieId).map(MovieDetailMapper::toMovieDetail)

    override fun addMoviesToDb(data: List<Movie>, type: MovieType): List<Movie> {
        Timber.tag(this.javaClass.simpleName).w("addMoviesToDb not impl, but called")
        return listOf() // выглядит как костыль, но пусть будет так
    }

    override fun getMovieCredits(movieId: Int): Single<Credits> =
        moviesService.getMovieCredits(movieId).map(CreditsMapper::toCredits)

    override fun getMoviesByCredits(personId: Int): Single<List<Movie>> =
        moviesService.getMoviesByCredits(personId).map {
            it.toModel()
        }

    override fun getPersonDetail(personId: Int): Single<PersonDetail> {
        return moviesService.getPersonDetail(personId).map(PersonDetailMapper::asAppModel)
    }
}