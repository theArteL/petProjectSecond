package com.artelsv.petprojectsecond.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artelsv.petprojectsecond.di.ViewModelKey
import com.artelsv.petprojectsecond.di.factory.ViewModelFactory
import com.artelsv.petprojectsecond.ui.AuthViewModel
import com.artelsv.petprojectsecond.ui.MainViewModel
import com.artelsv.petprojectsecond.ui.userlist.UserListViewModel
import com.artelsv.petprojectsecond.ui.moviedetail.MovieDetailViewModel
import com.artelsv.petprojectsecond.ui.movielist.MovieListViewModel
import com.artelsv.petprojectsecond.ui.persondetail.PersonDetailViewModel
import com.artelsv.petprojectsecond.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun providesMovieListViewModel(viewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun providesMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun providesAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun providesUserListViewModel(viewModel: UserListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun providesMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun providesProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun providesPersonDetailViewModel(viewModel: PersonDetailViewModel): ViewModel
}