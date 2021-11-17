package com.artelsv.petprojectsecond.data.datasource

import io.reactivex.Single

interface AuthDataSource {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        username: String,
        password: String,
    ): Single<String>
}