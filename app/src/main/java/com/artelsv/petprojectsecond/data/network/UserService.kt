package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.auth.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.auth.SessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.GuestSessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("/3/authentication/guest_session/new")
    fun createGuestSession(): Single<GuestSessionResponse>

    @GET("/3/authentication/token/new")
    fun createRequestToken(): Single<RequestTokenResponse>

    @POST("/3/authentication/session/new")
    fun createSession(@Body body: Map<String, String>): Single<SessionResponse>

    @POST("/3/authentication/token/validate_with_login")
    @JvmSuppressWildcards
    fun createSessionWithUser(@Body body: Map<String, String>): Single<SessionResponse>

    @GET("/3/account")
    fun getUser(@Query("session_id") sessionId: String): Single<UserResponse>
}