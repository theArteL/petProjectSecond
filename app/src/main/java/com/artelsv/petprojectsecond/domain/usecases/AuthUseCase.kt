package com.artelsv.petprojectsecond.domain.usecases

import io.reactivex.Single

interface AuthUseCase {
    operator fun invoke(): Single<String>
}