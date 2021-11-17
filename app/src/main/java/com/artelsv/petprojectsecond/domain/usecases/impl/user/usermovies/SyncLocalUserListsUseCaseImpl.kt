package com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies

import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.SyncLocalUserListsUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SyncLocalUserListsUseCaseImpl @Inject constructor(
    private val userMoviesRepository: UserMoviesRepository,
) : SyncLocalUserListsUseCase {
    override fun invoke(id: Int): Disposable = userMoviesRepository.syncLocalUserLists(id)
}