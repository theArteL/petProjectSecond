package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.AuthUserUseCase
import io.reactivex.Single
import javax.inject.Inject

class AuthUserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    AuthUserUseCase {

    override fun invoke(requestToken: String, login: String, password: String): Single<String> {
        return userRepository.createSessionWithUser(
            requestToken,
            login,
            password
        ).map {
            it.sessionId
        }
    }
}