package com.artelsv.petprojectsecond.data.network.model.auth.request

import com.google.gson.annotations.SerializedName

data class CreateSessionRequest(
    @SerializedName("request_token") val requestToken: String
)