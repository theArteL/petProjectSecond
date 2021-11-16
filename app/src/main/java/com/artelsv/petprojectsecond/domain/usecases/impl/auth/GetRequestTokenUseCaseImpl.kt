package com.artelsv.petprojectsecond.domain.usecases.impl.auth

import com.artelsv.petprojectsecond.domain.repository.AuthRepository
import com.artelsv.petprojectsecond.domain.usecases.auth.GetRequestTokenUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRequestTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) :
    GetRequestTokenUseCase {

    override fun invoke(): Single<String> {
        return authRepository.createRequestToken()
    }
}