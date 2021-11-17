package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.AuthDataSource
import com.artelsv.petprojectsecond.domain.repository.AuthRepository
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val preferenceManager: SharedPreferenceManager,
) : AuthRepository {
    override fun createGuestSession(): Single<String> {
        return authDataSource.createGuestSession()
            .map {
                preferenceManager.addGuestSession(it)
                it
            }
    }

    override fun createRequestToken(): Single<String> {
        return authDataSource.createRequestToken()
    }

    override fun createSession(requestToken: String): Single<String> {
        return authDataSource.createSession(requestToken)
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
        return authDataSource.createSessionWithUser(
            requestToken,
            login,
            password
        )
    }
}