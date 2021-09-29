package com.artelsv.petprojectsecond.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieResponse>,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)