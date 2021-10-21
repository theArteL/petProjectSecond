package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.ToggleFavorite
import com.artelsv.petprojectsecond.domain.usecases.MarkAsFavoriteUseCase
import io.reactivex.Single
import javax.inject.Inject

class MarkAsFavoriteUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): MarkAsFavoriteUseCase {
    override fun invoke(data: ToggleFavorite, accountId: Int, sessionId: String?): Single<Boolean> {
        return userRepository.toggleFavorite(data, accountId, sessionId)
    }
}