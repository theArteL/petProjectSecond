package com.artelsv.petprojectsecond.data.network.model.persondetail

import com.google.gson.annotations.SerializedName

data class MovieCastResponse(
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
)