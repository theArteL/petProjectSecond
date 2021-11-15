package com.artelsv.petprojectsecond.domain.model.movie.credits

data class Credits(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>,
)
