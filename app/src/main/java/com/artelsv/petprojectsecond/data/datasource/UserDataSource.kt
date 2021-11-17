package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single

interface UserDataSource {
    fun getUser(sessionId: String?): Single<UserResponse>
}