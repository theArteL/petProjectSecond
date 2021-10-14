package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.UserService
import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override fun createGuestSession(): Single<String> {
        return userService.createGuestSession().map {
            it.guestSessionId
        }
    }

    override fun createRequestToken(): Single<RequestTokenResponse> {
        return Single.error(Throwable("a"))
    }

    override fun createSession(requestToken: String): Single<SessionResponse> {
        return Single.error(Throwable("a"))
    }

    override fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<SessionResponse> {
        return Single.error(Throwable("a"))
    }
}