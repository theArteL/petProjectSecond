package com.artelsv.petprojectsecond.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artelsv.petprojectsecond.domain.model.*

@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
//    @SerializedName("belongs_to_collection") val belongsToCollection: String? = null,
    val budget: Int,
//    val genres: List<Genre>,
    val genreIds: List<Int>,
//    val homepage: String,
//    @SerializedName("imdb_id") val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
//    @SerializedName("production_companies") val productionCompanies: List<Company>,
//    @SerializedName("production_countries") val productionCountries: List<Country>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
//    @SerializedName("spoken_languages") val spokenLanguages: List<Language>,
//    val status: String,
//    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,

    val movieType: MovieType
)

//@Entity(tableName = "movies")
//data class MovieEntity (
//    @PrimaryKey
//    val id: Int,
//    val adult: Boolean,
//    val backdropPath: String,
//    val belongsToCollection: String?,
//    val budget: Int,
//    val genres: List<Genre>?,
//    val genreIds: List<Int>,
//    val homepage: String?,
//    val imdbId: String?,
//    val originalLanguage: String,
//    val originalTitle: String,
//    val overview: String,
//    val popularity: Double,
//    val posterPath: String,
//    val productionCompanies: List<Company>?,
//    val productionCountries: List<Country>?,
//    val releaseDate: String,
//    val revenue: Int,
//    val runtime: Int,
//    val spokenLanguages: List<Language>?,
//    val status: String?,
//    val tagline: String?,
//    val title: String,
//    val video: Boolean,
//    val voteAverage: Double,
//    val voteCount: Int,
//
//    val movieType: MovieType
//)