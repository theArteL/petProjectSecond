package com.artelsv.petprojectsecond.domain.model

data class Company(
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)
