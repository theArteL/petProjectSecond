package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserLocalDataSource
import com.artelsv.petprojectsecond.data.datasource.UserMoviesSource
import com.artelsv.petprojectsecond.data.mappers.MovieListMapper
import com.artelsv.petprojectsecond.data.mappers.RateMovieMapper
import com.artelsv.petprojectsecond.data.mappers.ToggleFavoriteMapper
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.model.movie.RateMovie
import com.artelsv.petprojectsecond.domain.model.movie.ToggleFavorite
import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserMoviesRepositoryImpl @Inject constructor(
    private val userMoviesRemoteSource: UserMoviesSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val preferenceManager: SharedPreferenceManager,
) : UserMoviesRepository {

    override fun getFavoriteMovies(accountId: Int): Single<MovieList> {
        return userMoviesRemoteSource.getFavoriteMovies(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addFavorites(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun getFavoriteTvShows(accountId: Int): Single<MovieList> {
        return userMoviesRemoteSource.getFavoriteTvShows(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addFavorites(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun getRatedMovies(accountId: Int): Single<MovieList> {
        return userMoviesRemoteSource.getRatedMovies(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addRated(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun getRatedTvShows(accountId: Int): Single<MovieList> {
        return userMoviesRemoteSource.getRatedTvShows(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addRated(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun syncLocalUserLists(id: Int): Disposable {
        return Single.merge(getFavoriteMovies(id), getFavoriteTvShows(id), getRatedMovies(id), getRatedTvShows(id))
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun toggleFavorite(
        data: ToggleFavorite,
        accountId: Int,
    ): Single<Boolean> {
        return userMoviesRemoteSource.toggleFavorite(
            ToggleFavoriteMapper.appModelToRequest(data),
            accountId,
            preferenceManager.getSession())
    }

    override fun rateMovie(
        data: RateMovie,
        movieId: Int,
    ): Single<Boolean> {
        return userMoviesRemoteSource.rateMovie(
            RateMovieMapper.toRequest(data),
            movieId,
            preferenceManager.getSession()
        ).map {
            val rep = userLocalDataSource.get(movieId)
            rep?.let { movieData ->
                userLocalDataSource.addRate(movieData.copy(rating = data.value.toFloat()))
            }

            it
        }
    }
}