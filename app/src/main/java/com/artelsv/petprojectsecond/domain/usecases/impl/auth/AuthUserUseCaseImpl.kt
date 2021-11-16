package com.artelsv.petprojectsecond.domain.usecases.impl.auth

import com.artelsv.petprojectsecond.domain.repository.AuthRepository
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthUserUseCase
import io.reactivex.Single
import javax.inject.Inject

class AuthUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) :
    AuthUserUseCase {

    override fun invoke(requestToken: String, login: String, password: String): Single<String> {
        return authRepository.createSessionWithUser(
            requestToken,
            login,
            password
        )
    }
}