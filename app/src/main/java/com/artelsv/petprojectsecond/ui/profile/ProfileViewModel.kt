package com.artelsv.petprojectsecond.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteTvShowsUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedTvShowsUseCase
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getFavoriteTvShowsUseCase: GetFavoriteTvShowsUseCase,
    private val getRatedMoviesUseCase: GetRatedMoviesUseCase,
    private val getRatedTvShowsUseCase: GetRatedTvShowsUseCase,

    private val router: Router,
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
            .subscribe({
                user.postValue(it)
                getUserLists(it.id)
                loading.postValue(false)
            }, {
                error.postValue(it)
            })
            .addToComposite()
    }

    fun exit(context: Context) {
        getUserUseCase.exit()
        router.newRootScreen(Screens.authActivity(context))
    }

    fun toggleUserList() {
        if (!userLists.value.isNullOrEmpty()) listOpen.value = !(listOpen.value ?: false) else error.postValue(Throwable("Пользовательские списки пусты"))
    }

    fun saveUri(value: Uri) {
        saveUri.postValue(value)
    }

    fun navigationBack() {
        router.exit()
    }

    private fun getUserLists(id: Int) {
        getFavoriteMoviesUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_favorite_movies))
                    userLists.value = userLists.value
                }
            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()

        getFavoriteTvShowsUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_favorite_tv_shows))
                    userLists.value = userLists.value
                }
            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()

        getRatedMoviesUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_rated_movies))
                    userLists.value = userLists.value
                }
            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()

        getRatedTvShowsUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.results.isNullOrEmpty()) {
                    userLists.value?.add(Pair(it, R.string.profile_movie_list_rated_tv_shows))
                    userLists.value = userLists.value
                }

                loading.postValue(false)
            }, {
                Timber.tag("profile").e(it)
            })
            .addToComposite()
    }
}