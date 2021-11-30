package com.artelsv.petprojectsecond.data.datasource.impl.user

import com.artelsv.petprojectsecond.data.database.dao.UserDao
import com.artelsv.petprojectsecond.data.database.dao.UserMovieDao
import com.artelsv.petprojectsecond.data.datasource.UserLocalDataSource
import com.artelsv.petprojectsecond.data.entity.UserMovieEntity
import com.artelsv.petprojectsecond.data.mappers.UserMapper
import com.artelsv.petprojectsecond.data.mappers.UserMovieMapper
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userListDao: UserMovieDao,
    private val userDao: UserDao,
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

    override fun addUser(user: User): User {
        if (getUser()?.id ?: EMPTY_USER_TABLE != user.id) {
            userDao.add(UserMapper.userToEntity(user))
        }

        return user
    }

    override fun getUser(): User? {
        return UserMapper.entityToUser(userDao.get())
    }

    companion object {
        private const val EMPTY_USER_TABLE = -1
    }
}