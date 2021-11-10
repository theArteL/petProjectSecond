package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.movie.CastResponse
import com.artelsv.petprojectsecond.data.network.model.movie.CreditsResponse
import com.artelsv.petprojectsecond.data.network.model.movie.CrewResponse
import com.artelsv.petprojectsecond.domain.model.movie.credits.Cast
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.movie.credits.Crew

object CreditsMapper {

    fun toCredits(data: CreditsResponse) = Credits(data.id, data.cast.map(::toCast), data.crew.map(::toCrew))

    private fun toCast(data: CastResponse) = Cast(
        adult = data.adult,
        gender = data.gender,
        id = data.id,
        knownForDepartment = data.knownForDepartment,
        name = data.name,
        originalName = data.originalName,
        popularity = data.popularity,
        profilePath = data.profilePath,
        castId = data.castId,
        character = data.character,
        creditId = data.creditId,
        order = data.order
    )

    private fun toCrew(data: CrewResponse) = Crew(
        adult = data.adult,
        gender = data.gender,
        id = data.id,
        knownForDepartment = data.knownForDepartment,
        name = data.name,
        originalName = data.originalName,
        popularity = data.popularity,
        profilePath = data.profilePath,
        creditId = data.creditId,
        department = data.department,
        job = data.job
    )
}