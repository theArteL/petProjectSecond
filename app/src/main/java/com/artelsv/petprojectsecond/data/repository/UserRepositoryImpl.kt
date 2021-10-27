package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.datasource.UserLocalDataSource
import com.artelsv.petprojectsecond.data.mappers.MovieListMapper
import com.artelsv.petprojectsecond.data.mappers.RateMovieMapper
import com.artelsv.petprojectsecond.data.mappers.ToggleFavoriteMapper
import com.artelsv.petprojectsecond.data.mappers.UserMapper
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.MovieList
import com.artelsv.petprojectsecond.domain.model.RateMovie
import com.artelsv.petprojectsecond.domain.model.ToggleFavorite
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val preferenceManager: SharedPreferenceManager,
) : UserRepository {
    private var user: User? = null

    override fun createGuestSession(): Single<String> {
        return userRemoteDataSource.createGuestSession()
            .map {
                preferenceManager.addGuestSession(it)
                it
            }
    }

    override fun createRequestToken(): Single<String> {
        return userRemoteDataSource.createRequestToken()
    }

    override fun createSession(requestToken: String): Single<String> {
        return userRemoteDataSource.createSession(requestToken)
            .map {
                preferenceManager.addSession(it)
                preferenceManager.addAuth(true)
                it
            }
    }

    override fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String,
    ): Single<String> {
        return userRemoteDataSource.createSessionWithUser(
            requestToken,
            login,
            password
        )
    }

    override fun getUser(): Single<User> {
        return if (preferenceManager.getAuth()) {
            userRemoteDataSource.getUser(
                preferenceManager.getSession()!!
            ).map {
                setUser(it)
            }
        } else {
            Single.error(Throwable("Not auth"))
        }
    }

    private fun setUser(mUser: UserResponse): User {
        user = UserMapper.userResponseToUser(mUser)
        return user!!
    }

    override fun getLocalUser(): User? {
        return user
    }

    override fun exit() {
        preferenceManager.removeAuth()
        preferenceManager.removeGuestSession()
        preferenceManager.removeSession()
    }

    override fun getFavoriteMovies(accountId: Int): Single<MovieList> {
        return userRemoteDataSource.getFavoriteMovies(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addFavorites(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun getFavoriteTvShows(accountId: Int): Single<MovieList> {
        return userRemoteDataSource.getFavoriteTvShows(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addFavorites(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun getRatedMovies(accountId: Int): Single<MovieList> {
        return userRemoteDataSource.getRatedMovies(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addRated(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun getRatedTvShows(accountId: Int): Single<MovieList> {
        return userRemoteDataSource.getRatedTvShows(accountId, preferenceManager.getSession())
            .map {
                userLocalDataSource.addRated(MovieListMapper.movieListResponseToMovieList(it))
                MovieListMapper.movieListResponseToMovieList(it)
            }
    }

    override fun toggleFavorite(
        data: ToggleFavorite,
        accountId: Int,
        sessionId: String?,
    ): Single<Boolean> {
        return userRemoteDataSource.toggleFavorite(
            ToggleFavoriteMapper.appModelToRequest(data),
            accountId,
            sessionId)
    }

    override fun rateMovie(data: RateMovie, movieId: Int, sessionId: String?): Single<Boolean> {
        return userRemoteDataSource.rateMovie(
            RateMovieMapper.toRequest(data),
            movieId,
            sessionId
        )
    }
}