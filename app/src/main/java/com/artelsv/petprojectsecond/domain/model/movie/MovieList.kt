package com.artelsv.petprojectsecond.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieList(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int,
) : Parcelable