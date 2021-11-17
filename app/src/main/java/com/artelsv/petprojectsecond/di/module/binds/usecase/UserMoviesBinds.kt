package com.artelsv.petprojectsecond.di.module.binds.usecase

import com.artelsv.petprojectsecond.domain.usecases.impl.user.usermovies.*
import com.artelsv.petprojectsecond.domain.usecases.user.usermovies.*
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

    @Binds
    abstract fun bindSyncLocalUserListsUseCase(syncLocalUserListsUseCaseImpl: SyncLocalUserListsUseCaseImpl): SyncLocalUserListsUseCase
}