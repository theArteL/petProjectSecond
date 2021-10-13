package com.artelsv.petprojectsecond.data.datasource

import android.service.autofill.UserData
import com.artelsv.petprojectsecond.data.network.UserService
import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.GuestSessionResponse
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override fun createGuestSession(): Single<GuestSessionResponse> {
        return userService.createGuestSession()
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