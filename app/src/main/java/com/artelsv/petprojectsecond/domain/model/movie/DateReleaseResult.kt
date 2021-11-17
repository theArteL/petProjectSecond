package com.artelsv.petprojectsecond.domain.model.movie

data class DateReleaseResult(
    val iso: String,
    val releaseDates: List<DateRelease>,
)