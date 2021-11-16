package com.artelsv.petprojectsecond.di.module.binds.usecase

import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetMovieDateReleaseUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetNowPlayingMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.GetPopularMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.movies.GetMovieDateReleaseUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.GetPopularMoviesUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class MoviesBinds {

    @Binds
    abstract fun bindGetMovieDateReleaseUseCase(getMovieDetailsUseCaseImpl: GetMovieDateReleaseUseCaseImpl): GetMovieDateReleaseUseCase

    @Binds
    abstract fun bindGetPopularMoviesUseCase(getPopularMoviesUseCaseImpl: GetPopularMoviesUseCaseImpl): GetPopularMoviesUseCase

    @Binds
    abstract fun bindGetNowPlayingMoviesUseCase(getNowPlayingMoviesUseCaseImpl: GetNowPlayingMoviesUseCaseImpl): GetNowPlayingMoviesUseCase
}