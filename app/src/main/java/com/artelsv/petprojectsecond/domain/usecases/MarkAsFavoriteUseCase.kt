package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.ToggleFavorite
import io.reactivex.Single

interface MarkAsFavoriteUseCase {
    operator fun invoke(data: ToggleFavorite, accountId: Int, sessionId: String?): Single<Boolean>
}