package com.artelsv.petprojectsecond.data.mappers.persondetail

import com.artelsv.petprojectsecond.data.network.model.persondetail.PersonDetailResponse
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail

object PersonDetailMapper {
    fun asAppModel(data: PersonDetailResponse) = PersonDetail(data.birthday,
        data.knownForDepartment,
        data.deathday,
        data.id,
        data.name,
        data.alsoKnownAs,
        data.gender,
        data.biography,
        data.popularity,
        data.placeOfBirth,
        data.profilePath,
        data.adult,
        data.imdbId,
        data.homepage)
}