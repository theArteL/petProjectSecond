package com.artelsv.petprojectsecond.domain.model

import com.google.gson.annotations.SerializedName

data class DateRelease(

    val certification : String,
    val iso : String?,
    val releaseDate : String,
    val type : Int
)
