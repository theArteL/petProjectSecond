package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.auth.GuestSessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.auth.SessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.request.CreateSessionRequest
import com.artelsv.petprojectsecond.data.network.model.auth.request.SessionWithUserRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @GET("/3/authentication/guest_session/new")
    fun createGuestSession(): Single<GuestSessionResponse>

    @GET("/3/authentication/token/new")
    fun createRequestToken(): Single<RequestTokenResponse>

    @POST("/3/authentication/session/new")
    fun createSession(@Body body: CreateSessionRequest): Single<SessionResponse>

    @POST("/3/authentication/token/validate_with_login")
    @JvmSuppressWildcards
    fun createSessionWithUser(@Body body: SessionWithUserRequest): Single<SessionResponse>
}