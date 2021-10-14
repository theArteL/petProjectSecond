package com.artelsv.petprojectsecond.domain.usecases

import io.reactivex.Single

interface AuthAsGuestUseCase {
    operator fun invoke(): Single<String>
}