package com.artelsv.petprojectsecond.di.module.binds.usecase

import com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies.GetFavoriteMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies.GetFavoriteTvShowUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies.GetRatedMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies.GetRatedTvShowsUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetFavoriteTvShowsUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.GetRatedTvShowsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UserMoviesBinds {

    @Binds
    abstract fun bindGetFavoriteMoviesUseCase(getFavoriteMoviesUseCaseImpl: GetFavoriteMoviesUseCaseImpl): GetFavoriteMoviesUseCase

    @Binds
    abstract fun bindGetFavoriteTvShowUseCase(getFavoriteTvShowUseCaseImpl: GetFavoriteTvShowUseCaseImpl): GetFavoriteTvShowsUseCase

    @Binds
    abstract fun bindRatedMoviesUseCase(getRatedMoviesUseCaseImpl: GetRatedMoviesUseCaseImpl): GetRatedMoviesUseCase

    @Binds
    abstract fun bindRatedTvShowsUseCase(getRatedTvShowsUseCaseImpl: GetRatedTvShowsUseCaseImpl): GetRatedTvShowsUseCase
}