package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.network.UserService
import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import com.artelsv.petprojectsecond.domain.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService) :
    UserRepository {
    override fun createRequestToken(): Single<RequestTokenResponse> {
        return userService.createRequestToken()
    }

    override fun createSession(requestToken: String): Single<SessionResponse> {
        return userService.createSession(hashMapOf(
            "request_token" to requestToken
        ))
    }

    override fun createSessionWithUser(requestToken: String, login: String, password: String): Single<SessionResponse> {
        return userService.createSession(hashMapOf(
            "request_token" to requestToken,
            "username" to login,
            "password" to login
        ))
    }
}