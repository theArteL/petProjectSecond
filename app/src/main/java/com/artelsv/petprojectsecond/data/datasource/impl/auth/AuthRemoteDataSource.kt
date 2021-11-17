package com.artelsv.petprojectsecond.data.datasource.impl.auth

import com.artelsv.petprojectsecond.data.datasource.AuthDataSource
import com.artelsv.petprojectsecond.data.network.AuthService
import com.artelsv.petprojectsecond.data.network.model.auth.request.CreateSessionRequest
import com.artelsv.petprojectsecond.data.network.model.auth.request.SessionWithUserRequest
import io.reactivex.Single
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override fun createGuestSession(): Single<String> {
        return authService.createGuestSession().map {
            it.guestSessionId
        }
    }

    override fun createRequestToken(): Single<String> {
        return authService.createRequestToken().map {
            it.requestToken
        }
    }

    override fun createSession(requestToken: String): Single<String> {
        return authService.createSession(
            CreateSessionRequest(
                requestToken = requestToken
            )
        ).map { it.sessionId }
    }

    override fun createSessionWithUser(
        requestToken: String,
        username: String,
        password: String,
    ): Single<String> {
        return authService.createSessionWithUser(
            SessionWithUserRequest(
                username = username,
                password = password,
                requestToken = requestToken
            )
        ).map { it.requestToken }
    }
}