package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.GuestSessionResponse
import io.reactivex.Single

interface UserDataSource {

    fun createGuestSession(): Single<GuestSessionResponse>
    fun createRequestToken(): Single<RequestTokenResponse>
    fun createSession(requestToken: String): Single<SessionResponse>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<SessionResponse>
}