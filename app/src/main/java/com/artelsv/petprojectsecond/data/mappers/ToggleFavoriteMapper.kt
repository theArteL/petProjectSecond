package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.ToggleFavoriteRequest
import com.artelsv.petprojectsecond.domain.model.movie.ToggleFavorite

object ToggleFavoriteMapper {
    fun appModelToRequest(value: ToggleFavorite) = ToggleFavoriteRequest(value.mediaType, value.mediaId, value.favorite)
}