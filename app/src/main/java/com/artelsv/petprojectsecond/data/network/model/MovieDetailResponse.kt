package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
//    @SerializedName("belongs_to_collection") val belongsToCollection: String?,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("production_companies") val productionCompanies: List<CompanyResponse>,
    @SerializedName("production_countries") val productionCountries: List<CountryResponse>,
    @SerializedName("spoken_languages") val spokenLanguages: List<LanguageResponse>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String?
)