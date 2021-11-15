package com.artelsv.petprojectsecond.data.network.model.persondetail

import com.google.gson.annotations.SerializedName

data class PersonDetailResponse(
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("deathday") val deathday: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    /** [name] на разных языках **/
    @SerializedName("also_known_as") val alsoKnownAs: List<String>,
    @SerializedName("gender") val gender: Int,
    @SerializedName("biography") val biography: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("homepage") val homepage: String?,
)
