package com.artelsv.petprojectsecond.data.repository

import com.artelsv.petprojectsecond.data.datasource.UserDataSource
import com.artelsv.petprojectsecond.data.mappers.UserMapper
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.repository.UserRepository
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val preferenceManager: SharedPreferenceManager,
) : UserRepository {
    private var user: User? = null

    override fun getUser(): Single<User> {
        return if (preferenceManager.isAuth()) {
            userRemoteDataSource.getUser(
                preferenceManager.getSession()
            ).map {
                setUser(it)
            }
        } else {
            Single.error(Throwable("Not auth"))
        }
    }

    private fun setUser(mUser: UserResponse): User {
        user = UserMapper.userResponseToUser(mUser)
        return user!!
    }

    override fun getLocalUser(): User? {
        return user
    }

    override fun exit() {
        preferenceManager.removeAuth()
        preferenceManager.removeGuestSession()
        preferenceManager.removeSession()
    }
}