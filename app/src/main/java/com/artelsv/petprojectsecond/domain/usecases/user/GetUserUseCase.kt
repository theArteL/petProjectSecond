package com.artelsv.petprojectsecond.domain.usecases.user

import com.artelsv.petprojectsecond.domain.model.User
import io.reactivex.Single

interface GetUserUseCase {
    operator fun invoke(): Single<User>

    fun getLocalUser(): User?
    fun exit()
}