package com.artelsv.petprojectsecond.data.datasource

import com.artelsv.petprojectsecond.data.database.dao.UserMovieDao
import com.artelsv.petprojectsecond.data.entity.UserMovieEntity
import com.artelsv.petprojectsecond.data.mappers.UserMovieMapper
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userListDao: UserMovieDao,
) : UserLocalDataSource {
    override fun addFavorites(data: MovieList) {
        userListDao.addAll(data.results.map {
            UserMovieMapper.toEntity(it, true)
        })
    }

    override fun addFavorite(data: UserMovieEntity) {
        userListDao.add(data)
    }

    override fun addRated(data: MovieList) {
        userListDao.addAll(data.results.map {
            UserMovieMapper.toEntity(it)
        })
    }

    override fun addRate(data: UserMovieEntity) {
        userListDao.add(data)
    }

    override fun get(id: Int) = userListDao.get(id)
}