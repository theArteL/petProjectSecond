package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseResponse
import com.artelsv.petprojectsecond.data.network.model.releasedate.DateReleaseResultsResponse
import com.artelsv.petprojectsecond.domain.model.movie.DateRelease
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDateResultMapperTest {

    private val dateReleaseResponse = DateReleaseResponse("cert", "iso", "11.11.2011", 0)
    private val dateReleaseResultListResponse =
        DateReleaseResultsResponse("iso", listOf(dateReleaseResponse, dateReleaseResponse))

    private val dateRelease = DateRelease("cert", "iso", "11.11.2011", 0)
    private val dateReleaseResultList = DateReleaseResult("iso", listOf(dateRelease, dateRelease))

    @Test
    fun toDateRelease() {
        assertEquals(dateRelease, MovieDateResultMapper.toDateRelease(dateReleaseResponse))
    }

    @Test
    fun toDateReleaseResult() {
        assertEquals(
            dateReleaseResultList,
            MovieDateResultMapper.toDateReleaseResult(dateReleaseResultListResponse)
        )
    }
}