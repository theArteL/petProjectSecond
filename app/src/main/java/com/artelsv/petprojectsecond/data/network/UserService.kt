package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/3/account")
    fun getUser(@Query("session_id") sessionId: String): Single<UserResponse>
}