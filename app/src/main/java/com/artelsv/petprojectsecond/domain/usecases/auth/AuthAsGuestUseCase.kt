package com.artelsv.petprojectsecond.domain.usecases.auth

import io.reactivex.Single

interface AuthAsGuestUseCase {
    operator fun invoke(): Single<String>
}