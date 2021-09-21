package com.artelsv.petprojectsecond.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    @SerializedName("iso_3166_1") val iso: String,
    val name: String
) : Parcelable