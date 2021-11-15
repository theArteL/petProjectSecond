package com.artelsv.petprojectsecond.data.network

import com.artelsv.petprojectsecond.data.network.model.DefaultResponse
import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.data.network.model.RateMovieRequest
import com.artelsv.petprojectsecond.data.network.model.ToggleFavoriteRequest
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

    @GET("/3/account/{account_id}/favorite/movies") //sort_by=created_at.asc&{
    fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String?,
        @Query("page") page: Int = 1,
    ): Single<MovieListResponse>

    @GET("/3/account/{account_id}/favorite/tv")
    fun getFavoriteTv(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String?,
        @Query("page") page: Int = 1,
    ): Single<MovieListResponse>

    @GET("/3/account/{account_id}/rated/movies")
    fun getRatedMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String?,
        @Query("page") page: Int = 1,
    ): Single<MovieListResponse>

    @GET("/3/account/{account_id}/rated/tv")
    fun getRatedTvShows(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String?,
        @Query("page") page: Int = 1,
    ): Single<MovieListResponse>

    @POST("/3/account/{account_id}/favorite")
    fun toggleFavorite(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String?,
        @Body body: ToggleFavoriteRequest
    ): Single<DefaultResponse>

    @POST("/3/movie/{movie_id}/rating")
    fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String?,
        @Body body: RateMovieRequest
    ): Single<DefaultResponse>
}