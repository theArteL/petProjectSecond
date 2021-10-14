package com.artelsv.petprojectsecond.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class GuestSessionResponse(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("expires_at") val expiresAt: String? = null,
    @SerializedName("guest_session_id") val guestSessionId: String? = null,
    @SerializedName("status_message") val statusMessage: String? = null,
    @SerializedName("status_code") val statusCode: Int? = null
)