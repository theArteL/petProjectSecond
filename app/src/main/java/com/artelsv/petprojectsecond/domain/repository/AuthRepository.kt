package com.artelsv.petprojectsecond.domain.repository

import io.reactivex.Single

interface AuthRepository {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String,
    ): Single<String>
}