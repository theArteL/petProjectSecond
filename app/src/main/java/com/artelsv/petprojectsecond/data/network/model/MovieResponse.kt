package com.artelsv.petprojectsecond.data.network.model

import com.artelsv.petprojectsecond.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)