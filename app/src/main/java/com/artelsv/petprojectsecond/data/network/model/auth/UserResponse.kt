package com.artelsv.petprojectsecond.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar")
    val avatar: AvatarResponse,
    @SerializedName("id")
    val id: Int,
    @SerializedName("iso_639_1")
    val isoLang: String,
    @SerializedName("iso_3166_1")
    val isoCountry: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("include_adult")
    val includeAdult: String,
    @SerializedName("username")
    val username: String
)

// не стал выносить в отдельный класс, ибо не вижу смысла
data class AvatarResponse(
    @SerializedName("gravatar") val gravatar : GravatarResponse,
    @SerializedName("tmdb") val tmdb : TmdbResponse
)

data class TmdbResponse (

    @SerializedName("avatar_path") val avatarPath : String?
)
data class GravatarResponse (

    @SerializedName("hash") val hash : String
)
