package com.artelsv.petprojectsecond.domain.model

data class ToggleFavorite(
    val mediaType: String,
    val mediaId: Int,
    val favorite: Boolean
)