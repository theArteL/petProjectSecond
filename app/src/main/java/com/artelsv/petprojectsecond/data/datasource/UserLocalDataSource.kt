package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.entity.UserMovieEntity
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.model.movie.MovieList

interface UserLocalDataSource {

    fun addFavorites(data: MovieList)
    fun addFavorite(data: UserMovieEntity)
    fun addRated(data: MovieList)
    fun addRate(data: UserMovieEntity)
    fun get(id: Int): UserMovieEntity?

    fun addUser(user: User): User
    fun getUser(): User?
}