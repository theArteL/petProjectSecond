package com.artelsv.petprojectsecond.domain

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