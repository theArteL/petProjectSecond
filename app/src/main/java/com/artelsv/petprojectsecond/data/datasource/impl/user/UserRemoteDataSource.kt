package com.artelsv.petprojectsecond.data.datasource.impl.user

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.network.UserService
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService,
) : UserDataSource {

    override fun getUser(sessionId: String?): Single<UserResponse> {
        return if (!sessionId.isNullOrEmpty()) userService.getUser(sessionId) else Single.error(Throwable(SESSION_IS_EMPTY))
    }

    companion object {
        private const val SESSION_IS_EMPTY = "Значение сессии пустое, или равно нуль"
    }
}