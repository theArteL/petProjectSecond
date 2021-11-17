package com.artelsv.petprojectsecond.data.network.model.movie

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<CastResponse>,
    @SerializedName("crew") val crew: List<CrewResponse>,
)