package com.artelsv.petprojectsecond.domain.usecases.impl.user

import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.repository.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserUseCase {

    override fun invoke(): Single<User> {
        return userRepository.getUser()
    }

    override fun getLocalUser(): User? {
        return userRepository.getLocalUser()
    }

    override fun exit() {
        userRepository.exit()
    }
}