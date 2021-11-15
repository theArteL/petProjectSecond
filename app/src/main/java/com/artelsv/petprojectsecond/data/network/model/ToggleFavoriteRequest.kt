package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class ToggleFavoriteRequest(
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("favorite") val favorite: Boolean
)
