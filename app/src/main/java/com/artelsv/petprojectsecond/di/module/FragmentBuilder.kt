package com.artelsv.petprojectsecond.di.module

import com.artelsv.petprojectsecond.ui.moviedetail.MovieDetailFragment
import com.artelsv.petprojectsecond.ui.movielist.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributesMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributesMovieDetailFragment(): MovieDetailFragment
}