package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class LanguageResponse(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso: String,
    @SerializedName("name") val name: String
)