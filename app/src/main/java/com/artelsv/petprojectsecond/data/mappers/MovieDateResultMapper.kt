package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseResponse
import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseResultsResponse
import com.artelsv.petprojectsecond.domain.model.movie.DateRelease
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult

object MovieDateResultMapper {
    fun toDateRelease(date: DateReleaseResponse) =
        DateRelease(date.certification, date.iso, date.releaseDate, date.type)

    fun toDateReleaseResult(result: DateReleaseResultsResponse) =
        DateReleaseResult(result.iso, result.releaseDates.map(::toDateRelease))
}