package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.data.network.model.auth.GuestSessionResponse
import io.reactivex.Single

interface AuthAsGuestUseCase {
    operator fun invoke(): Single<GuestSessionResponse>
}