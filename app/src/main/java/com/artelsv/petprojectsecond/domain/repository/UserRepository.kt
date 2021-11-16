package com.artelsv.petprojectsecond.domain.repository

import com.artelsv.petprojectsecond.domain.model.User
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
    fun getLocalUser(): User?
    fun exit()
}