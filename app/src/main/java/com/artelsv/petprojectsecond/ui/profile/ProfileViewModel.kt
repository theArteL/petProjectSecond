package com.artelsv.petprojectsecond.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.UserListsUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        getUserUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                user.postValue(it)
                getUserLists(it.id)
                loading.postValue(false)
            }
            .subscribe({

            }, {
                error.postValue(it)
            })
            .addToComposite()
    }

    fun exit() {
        getUserUseCase.exit()
    }

    fun toggleUserList() {
        if (!userLists.value.isNullOrEmpty()) listOpen.value = !listOpen.value!! else error.postValue(Throwable("Пользовательские списки пусты"))
    }

    private fun getUserLists(id: Int) {
        userListsUseCase.getFavoriteMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_favorite_movies))
                    userLists.value = userLists.value
                }
            }
            .subscribe({

            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()

        userListsUseCase.getFavoriteTvShows(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_favorite_tv_shows))
                    userLists.value = userLists.value
                }
            }
            .subscribe({

            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()

        userListsUseCase.getRatedMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_rated_movies))
                    userLists.value = userLists.value
                }
            }
            .subscribe({

            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()

        userListsUseCase.getRatedMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_rated_tv_shows))
                    userLists.value = userLists.value
                }

                loading.postValue(false)
            }
            .subscribe({

            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()
    }
}