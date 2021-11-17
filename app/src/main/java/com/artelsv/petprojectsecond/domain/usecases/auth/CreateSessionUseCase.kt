package com.artelsv.petprojectsecond.domain.usecases.auth

import io.reactivex.Single

interface CreateSessionUseCase {
    operator fun invoke(requestToken: String): Single<String>
}