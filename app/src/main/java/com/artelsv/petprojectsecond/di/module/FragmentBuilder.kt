package com.artelsv.petprojectsecond.di.module

import com.artelsv.petprojectsecond.ui.moviedetail.MovieDetailFragment
import com.artelsv.petprojectsecond.ui.movielist.MovieListFragment
import com.artelsv.petprojectsecond.ui.persondetail.PersonDetailFragment
import com.artelsv.petprojectsecond.ui.profile.ProfileFragment
import com.artelsv.petprojectsecond.ui.userlist.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributesMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributesMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun contributesProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributesUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun contributesPersonDetailFragment(): PersonDetailFragment
}