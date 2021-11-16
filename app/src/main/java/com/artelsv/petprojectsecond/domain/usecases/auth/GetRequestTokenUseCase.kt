package com.artelsv.petprojectsecond.domain.usecases.auth

import io.reactivex.Single

interface GetRequestTokenUseCase {
    operator fun invoke(): Single<String>
}