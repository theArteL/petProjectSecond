package com.artelsv.petprojectsecond.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.MovieList
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.UserListsUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userListsUseCase: UserListsUseCase
) : BaseViewModel() {
    val loading = MutableLiveData(true)
    val error = MutableLiveData<Throwable>(null)

    val listOpen = MutableLiveData(false)
    val userLists = MutableLiveData<ArrayList<Pair<MovieList, Int>>>(arrayListOf())

    val user = MutableLiveData<User>(null)
    val saveUri = MutableLiveData<Uri>(null)

    init {
        compositeDisposable.add(getUserUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                user.postValue(it)
                loading.postValue(false)
            }
            .subscribe({

            }, {
                error.postValue(it)
            })
        )

        compositeDisposable.add(userListsUseCase.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { userLists.value?.add(Pair(it, R.string.profile_movie_list_favorite_movies)) }
            .subscribe({

            }, {
                Timber.tag("profile").e(it)
            })
        )
    }

    fun exit() {
        getUserUseCase.exit()
    }

    fun toggleUserList() {
        listOpen.value = !listOpen.value!!
    }
}