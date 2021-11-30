package com.artelsv.petprojectsecond.ui

import android.content.Context
import android.content.Intent
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.ui.moviedetail.MovieDetailFragment
import com.artelsv.petprojectsecond.ui.movielist.MovieListFragment
import com.artelsv.petprojectsecond.ui.persondetail.PersonDetailFragment
import com.artelsv.petprojectsecond.ui.profile.ProfileFragment
import com.artelsv.petprojectsecond.ui.userlist.UserListFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object Screens {
    fun mainActivity(context: Context) = ActivityScreen { Intent(context, MainActivity::class.java) }
    fun authActivity(context: Context) = ActivityScreen { Intent(context, AuthActivity::class.java) }

    fun movieList() = FragmentScreen { MovieListFragment() }
    fun movieDetail(movieId: Int) = FragmentScreen { MovieDetailFragment.newInstance(movieId) }
    fun profile() = FragmentScreen { ProfileFragment() }
    fun personDetail(value: Any) = FragmentScreen { PersonDetailFragment.newInstance(value) }
    fun userList(value: Pair<MovieList, Int>) = FragmentScreen { UserListFragment.newInstance(value) }
}