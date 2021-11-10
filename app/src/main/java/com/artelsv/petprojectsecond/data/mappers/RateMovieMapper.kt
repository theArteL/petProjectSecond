package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.RateMovieRequest
import com.artelsv.petprojectsecond.domain.model.movie.RateMovie

object RateMovieMapper {

    fun toRequest(value: RateMovie) = RateMovieRequest(value.value)

    fun toModel(value: RateMovieRequest) = RateMovie(value.value)
}