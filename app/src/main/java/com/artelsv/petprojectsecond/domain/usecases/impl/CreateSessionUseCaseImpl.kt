package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.CreateSessionUseCase
import io.reactivex.Single
import javax.inject.Inject

class CreateSessionUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    CreateSessionUseCase {

    override fun invoke(requestToken: String): Single<String> {
        return userRepository.createSession(
            requestToken
        )
    }
}