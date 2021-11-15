package com.artelsv.petprojectsecond.data.network.model.releasedate

import com.google.gson.annotations.SerializedName

data class DateReleaseResultsResponse (

    @SerializedName("iso_3166_1") val iso : String,
    @SerializedName("release_dates") val releaseDates : List<DateReleaseResponse>
)