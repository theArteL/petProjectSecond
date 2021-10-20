package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.auth.RequestTokenResponse
import com.artelsv.petprojectsecond.data.network.model.auth.SessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.GuestSessionResponse
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import io.reactivex.Single
import retrofit2.http.*

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

    @GET("/3/account/{account_id}/favorite/movies?sort_by=created_at.asc&{page}")
    fun getFavoriteMovies(@Path("account_id") accountId: Int, @Path("page") page: Int = 1): Single<MovieListResponse>

    @GET("/3/account/{account_id}/favorite/tv?sort_by=created_at.asc&{page}")
    fun getFavoriteTv(@Path("account_id") accountId: Int, @Path("page") page: Int = 1): Single<MovieListResponse>

    @GET("/3/account")
    fun getRatedMovies(@Query("session_id") sessionId: String): Single<MovieListResponse>

    @GET("/3/account")
    fun getRatedTvShows(@Query("session_id") sessionId: String): Single<MovieListResponse>
}