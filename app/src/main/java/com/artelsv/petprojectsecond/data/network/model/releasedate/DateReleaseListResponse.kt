package com.artelsv.petprojectsecond.data.network.model.releasedate

import com.google.gson.annotations.SerializedName

data class DateReleaseListResponse (

    @SerializedName("id") val id : Int,
    @SerializedName("results") val results : List<DateReleaseResultsResponse>
)