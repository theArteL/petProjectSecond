package com.artelsv.petprojectsecond.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteTvShowsUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedTvShowsUseCase
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.ui.userlist.UserListFragment
import com.artelsv.petprojectsecond.utils.exts.SingleLiveEvent
import com.github.terrakok.cicerone.Router
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@ExperimentalCoroutinesApi
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getFavoriteTvShowsUseCase: GetFavoriteTvShowsUseCase,
    private val getRatedMoviesUseCase: GetRatedMoviesUseCase,
    private val getRatedTvShowsUseCase: GetRatedTvShowsUseCase,

    private val context: Context,
    private val router: Router,
) : BaseViewModel() {
    val loading = MutableLiveData(true)
    val error = MutableLiveData<Throwable>(null)

    val listOpen = MutableLiveData(false)
    val userLists = MutableLiveData<ArrayList<Pair<MovieList, Int>>>(arrayListOf())

    val user = MutableLiveData<User>(null)
    val saveUri = MutableLiveData<Uri>(null)

    private var fragmentManager: FragmentManager? = null

    val userListAdapter = UserListAdapter {
        fragmentManager?.let { fm ->
            UserListFragment.newInstance(it).show(
                fm,
                UserListFragment::class.java.simpleName
            )
        }
    }

    val isTakePicture = SingleLiveEvent<Boolean>()

    fun setup(fragmentManager: FragmentManager) {
        getUser()
        this.fragmentManager = fragmentManager
    }

    fun exit() {
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

    fun takePicture() {
        isTakePicture.call()
    }

    private fun getUser() = getUserUseCase()
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

    private fun getUserLists(id: Int) {
        Single.zip(
            getFavoriteMoviesUseCase(id),
            getFavoriteTvShowsUseCase(id),
            getRatedMoviesUseCase(id),
            getRatedTvShowsUseCase(id),
            { favoriteMovies, favoriteTvShows, ratedMovies, ratedTvShows ->
                val userLists = arrayListOf<Pair<MovieList, Int>>()

                if (!favoriteMovies.results.isNullOrEmpty()) {
                    userLists.add(Pair(favoriteMovies, R.string.profile_movie_list_favorite_movies))
                }

                if (!favoriteTvShows.results.isNullOrEmpty()) {
                    userLists.add(Pair(favoriteTvShows, R.string.profile_movie_list_favorite_tv_shows))
                }

                if (!ratedMovies.results.isNullOrEmpty()) {
                    userLists.add(Pair(ratedMovies, R.string.profile_movie_list_rated_movies))
                }

                if (!ratedTvShows.results.isNullOrEmpty()) {
                    userLists.add(Pair(ratedTvShows, R.string.profile_movie_list_rated_tv_shows))
                }

                this.userLists.postValue(userLists)
                loading.postValue(false)
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, { Timber.tag("profile").e(it) }).addToComposite()
    }
}