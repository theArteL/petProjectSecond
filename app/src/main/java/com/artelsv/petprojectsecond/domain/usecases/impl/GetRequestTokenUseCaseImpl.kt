package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.GetRequestTokenUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRequestTokenUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    GetRequestTokenUseCase {

    override fun invoke(): Single<String> {
        return userRepository.createRequestToken()
    }
}