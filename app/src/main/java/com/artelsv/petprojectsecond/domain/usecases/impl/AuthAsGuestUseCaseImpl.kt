package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.AuthAsGuestUseCase
import io.reactivex.Single
import javax.inject.Inject

class AuthAsGuestUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : AuthAsGuestUseCase {
    override fun invoke(): Single<String> {
        return userRepository.createGuestSession()
    }
}