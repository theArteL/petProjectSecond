package com.artelsv.petprojectsecond.domain.usecases.impl.auth

import com.artelsv.petprojectsecond.domain.repository.AuthRepository
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthAsGuestUseCase
import io.reactivex.Single
import javax.inject.Inject

class AuthAsGuestUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : AuthAsGuestUseCase {
    override fun invoke(): Single<String> {
        return authRepository.createGuestSession()
    }
}