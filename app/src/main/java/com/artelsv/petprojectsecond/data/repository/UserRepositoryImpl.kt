package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.mappers.MovieListMapper
import com.artelsv.petprojectsecond.data.mappers.UserMapper
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.MovieList
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val preferenceManager: SharedPreferenceManager
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
        password: String
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

    override fun getFavoriteMovies(): Single<MovieList> {
//        user?.let {
//            return userRemoteDataSource.getFavoriteMovies(it.id).map(MovieListMapper::movieListResponseToMovieList)
//        }
    return userRemoteDataSource.getFavoriteMovies(getLocalUser()!!.id).map(MovieListMapper::movieListResponseToMovieList)
//        return Single.error(Throwable("account is null"))
    }

    override fun getFavoriteTvShows(): Single<MovieList> {
        TODO("Not yet implemented")
    }
}