package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.entity.UserMovieEntity
import com.artelsv.petprojectsecond.domain.model.MovieList

interface UserLocalDataSource {

    fun addFavorites(data: MovieList)
    fun addRated(data: MovieList)
    fun get(id: Int): UserMovieEntity?
}