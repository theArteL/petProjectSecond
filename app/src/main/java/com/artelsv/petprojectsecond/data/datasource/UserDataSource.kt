package com.artelsv.petprojectsecond.data.datasource

import io.reactivex.Single

interface UserDataSource {

    fun createGuestSession(): Single<String>
    fun createRequestToken(): Single<String>
    fun createSession(requestToken: String): Single<String>
    fun createSessionWithUser(
        requestToken: String,
        login: String,
        password: String
    ): Single<String>
}