package com.artelsv.petprojectsecond.domain.model

data class User(
    val avatar: Avatar,
    val id: Int,
    val isoLang: String,
    val isoCountry: String,
    val name: String,
    val includeAdult: String,
    val username: String
)

// не стал выносить в отдельный класс, ибо не вижу смысла
data class Avatar(
    val gravatar : Gravatar,
    val tmdb : Tmdb
)

data class Tmdb(
    val avatarPath : String?
)
data class Gravatar (
    val hash : String
)