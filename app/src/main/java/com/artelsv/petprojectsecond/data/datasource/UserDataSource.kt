package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import io.reactivex.Single

interface UserDataSource {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<SessionResponse>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<SessionResponse>
}