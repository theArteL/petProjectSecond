package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.datasource.UserLocalDataSource
import com.artelsv.petprojectsecond.data.mappers.UserMapper
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.repository.UserRepository
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val preferenceManager: SharedPreferenceManager,
) : UserRepository {

    override fun getUser(): Single<User> {
        return if (preferenceManager.isAuth()) {
            userRemoteDataSource.getUser(
                preferenceManager.getSession()
            ).map {
                userLocalDataSource.addUser(user = UserMapper.userResponseToUser(it))
            }
        } else {
            Single.error(Throwable("Not auth"))
        }
    }

    override fun getLocalUser(): User? {
        return userLocalDataSource.getUser()
    }

    override fun exit() {
        preferenceManager.removeAuth()
        preferenceManager.removeGuestSession()
        preferenceManager.removeSession()
    }
}