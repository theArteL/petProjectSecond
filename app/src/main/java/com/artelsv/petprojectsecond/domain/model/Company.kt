package com.artelsv.petprojectsecond.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    val name: String,
    @SerializedName("origin_country") val originCountry: String
) : Parcelable
