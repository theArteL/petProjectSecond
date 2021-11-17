package com.artelsv.petprojectsecond.domain.model.persondetail

data class PersonDetail(
    val birthday : String?,
    val knownForDepartment : String,
    val deathday : String?,
    val id : Int,
    val name : String,
    /** [name] на разных языках **/
    val alsoKnownAs : List<String>,
    val gender : Int,
    val biography : String,
    val popularity : Double,
    val placeOfBirth : String?,
    val profilePath : String?,
    val adult : Boolean,
    val imdbId : String,
    val homepage : String?
)