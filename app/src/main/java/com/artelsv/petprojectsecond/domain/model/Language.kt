package com.artelsv.petprojectsecond.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Language(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso: String,
    val name: String
) : Parcelable