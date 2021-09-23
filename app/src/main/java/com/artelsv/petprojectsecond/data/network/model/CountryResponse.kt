package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("iso_3166_1") val iso: String,
    @SerializedName("name") val name: String
)