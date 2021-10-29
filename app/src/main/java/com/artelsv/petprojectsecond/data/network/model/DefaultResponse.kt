package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("success") val success: Boolean = false,
)