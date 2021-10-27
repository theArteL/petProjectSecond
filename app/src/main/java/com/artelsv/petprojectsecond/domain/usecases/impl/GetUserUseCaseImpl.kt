package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserUseCase {
    private val dis = CompositeDisposable()

    override fun invoke(): Single<User> {
        return userRepository.getUser()
    }

    override fun getLocalUser(): User? {
        return userRepository.getLocalUser()
    }

    override fun exit() {
        userRepository.exit()
    }

    override fun syncLocalUserLists(id: Int) {
        dis.add(userRepository.getFavoriteMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
        )

        dis.add(userRepository.getFavoriteTvShows(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
        )

        dis.add(userRepository.getRatedMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
        )

        dis.add(userRepository.getRatedTvShows(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
        )
    }

    override fun dispose() {
        dis.dispose()
    }


}