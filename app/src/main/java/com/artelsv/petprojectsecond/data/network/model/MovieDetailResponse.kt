package com.artelsv.petprojectsecond.data.network.model

import com.artelsv.petprojectsecond.domain.model.Company
import com.artelsv.petprojectsecond.domain.model.Country
import com.artelsv.petprojectsecond.domain.model.Genre
import com.artelsv.petprojectsecond.domain.model.Language
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    val budget: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("belongs_to_collection") val belongsToCollection: String,
    val genres: List<Genre>,
    val homepage: String,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("production_companies") val productionCompanies: List<Company>,
    @SerializedName("production_countries") val productionCountries: List<Country>,
    @SerializedName("spoken_languages") val spokenLanguages: List<Language>,
    val status: String,
    val tagline: String,

    @SerializedName("status_message") val statusMessage: String? = "",
    @SerializedName("success") val success: Boolean? = true,
    @SerializedName("status_code") val statusCode: Int? = null,
)