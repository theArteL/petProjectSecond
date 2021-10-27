package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.database.dao.UserMovieDao
import com.artelsv.petprojectsecond.data.mappers.UserMovieMapper
import com.artelsv.petprojectsecond.domain.model.MovieList
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userListDao: UserMovieDao
): UserLocalDataSource {
    override fun addFavorites(data: MovieList) {
        userListDao.addAll(data.results.map {
            UserMovieMapper.toEntity(it, true)
        })
    }

    override fun addRated(data: MovieList) {
        userListDao.addAll(data.results.map {
            UserMovieMapper.toEntity(it)
        })
    }
}