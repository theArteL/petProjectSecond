package com.artelsv.petprojectsecond.domain.usecases.user.usermovies

import io.reactivex.disposables.Disposable

interface SyncLocalUserListsUseCase {
    operator fun invoke(id: Int): Disposable
}