package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class RateMovieRequest(
    @SerializedName("value") val value: Number
)