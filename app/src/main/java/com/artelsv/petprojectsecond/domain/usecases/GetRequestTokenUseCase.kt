package com.artelsv.petprojectsecond.domain.usecases

import io.reactivex.Single

interface GetRequestTokenUseCase {
    operator fun invoke(): Single<String>
}