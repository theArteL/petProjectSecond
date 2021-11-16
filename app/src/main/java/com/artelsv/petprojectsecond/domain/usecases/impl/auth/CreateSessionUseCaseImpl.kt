package com.artelsv.petprojectsecond.domain.usecases.impl.auth

import com.artelsv.petprojectsecond.domain.repository.AuthRepository
import com.artelsv.petprojectsecond.domain.usecases.auth.CreateSessionUseCase
import io.reactivex.Single
import javax.inject.Inject

class CreateSessionUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) :
    CreateSessionUseCase {

    override fun invoke(requestToken: String): Single<String> {
        return authRepository.createSession(
            requestToken
        )
    }
}