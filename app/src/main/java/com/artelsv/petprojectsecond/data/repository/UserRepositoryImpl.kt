package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.mappers.UserMapper
import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val preferenceManager: SharedPreferenceManager
) : UserRepository {
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
        return userRemoteDataSource.getUser(
            preferenceManager.getSession() ?: return Single.error(Throwable("Session is empty"))
        ).map(UserMapper::userResponseToUser)
    }
}