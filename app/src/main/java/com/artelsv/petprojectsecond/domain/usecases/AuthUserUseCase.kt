package com.artelsv.petprojectsecond.domain.usecases

import io.reactivex.Single

interface AuthUserUseCase {
    operator fun invoke(requestToken: String, login: String, password: String): Single<String>
}