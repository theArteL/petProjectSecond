package com.artelsv.petprojectsecond.domain.model.movie

data class ToggleFavorite(
    val mediaType: String,
    val mediaId: Int,
    val favorite: Boolean
)