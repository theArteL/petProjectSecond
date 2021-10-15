package com.artelsv.petprojectsecond.domain

import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import io.reactivex.Single

interface UserRepository {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<String>
}