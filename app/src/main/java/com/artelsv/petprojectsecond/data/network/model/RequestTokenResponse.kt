package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    val success: Boolean? = null,
    @SerializedName("expires_at") val expiresAt: String? = null,
    @SerializedName("request_token") val requestToken: String? = null,
    @SerializedName("status_message") val statusMessage: String? = null,
    @SerializedName("status_code") val statusCode: Int? = null
)