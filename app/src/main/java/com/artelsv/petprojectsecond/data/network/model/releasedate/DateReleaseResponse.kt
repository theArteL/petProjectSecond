package com.artelsv.petprojectsecond.data.network.model.releasedate

import com.google.gson.annotations.SerializedName

data class DateReleaseResponse (

    @SerializedName("certification") val certification : String,
    @SerializedName("iso_639_1") val iso : String?,
    @SerializedName("release_date") val releaseDate : String,
    @SerializedName("type") val type : Int
)