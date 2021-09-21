package com.artelsv.petprojectsecond.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artelsv.petprojectsecond.di.ViewModelKey
import com.artelsv.petprojectsecond.di.factory.ViewModelFactory
import com.artelsv.petprojectsecond.ui.movie_detail.MovieDetailViewModel
import com.artelsv.petprojectsecond.ui.movie_list.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
}