package com.artelsv.petprojectsecond.di.module.binds.usecase

import com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail.GetMovieCreditsUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail.GetMovieDetailsUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail.RateMovieUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail.ToggleFavoriteMovieUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieCreditsUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.RateMovieUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.ToggleFavoriteMovieUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailBinds {

    @Binds
    abstract fun bindGetMovieDetailsUseCase(getMovieDetailsUseCaseImpl: GetMovieDetailsUseCaseImpl): GetMovieDetailsUseCase

    @Binds
    abstract fun bindGetMovieCreditsUseCase(getMovieCreditsUseCase: GetMovieCreditsUseCaseImpl): GetMovieCreditsUseCase

    @Binds
    abstract fun bindRateMovieUseCase(rateMovieUseCase: RateMovieUseCaseImpl): RateMovieUseCase

    @Binds
    abstract fun bindToggleFavoriteMovieUseCase(toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCaseImpl): ToggleFavoriteMovieUseCase
}