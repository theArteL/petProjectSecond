package com.artelsv.petprojectsecond.data.network.model.auth.request

import com.google.gson.annotations.SerializedName

data class SessionWithUserRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("request_token") val requestToken: String,
)