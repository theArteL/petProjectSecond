package com.artelsv.petprojectsecond.domain.usecases

import io.reactivex.Single

interface CreateSessionUseCase {
    operator fun invoke(requestToken: String): Single<String>
}