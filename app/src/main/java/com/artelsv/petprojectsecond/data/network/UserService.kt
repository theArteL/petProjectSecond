package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.SessionResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("/authentication/token/new")
    fun createRequestToken() : Single<RequestTokenResponse>

    @POST("authentication/session/new")
    fun createSession(@Body body: HashMap<*, *>) : Single<SessionResponse>

    @POST("authentication/token/validate_with_login")
    fun createSessionWithUser(@Body body: HashMap<*, *>) : Single<SessionResponse>
}