package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import com.artelsv.petprojectsecond.domain.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource
) : UserRepository {
    override fun createGuestSession(): Single<String> {
        return userRemoteDataSource.createGuestSession()
    }

    override fun createRequestToken(): Single<String> {
        return userRemoteDataSource.createRequestToken()
    }

    override fun createSession(requestToken: String): Single<SessionResponse> {
        return userRemoteDataSource.createSession(
            requestToken
        )
    }

    override fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<SessionResponse> {
        return userRemoteDataSource.createSession(
            requestToken
        )
    }
}