package com.artelsv.petprojectsecond.di.module.binds.usecase

import com.artelsv.petprojectsecond.domain.usecases.auth.AuthAsGuestUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.CreateSessionUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.GetRequestTokenUseCase
import com.artelsv.petprojectsecond.domain.usecases.impl.auth.AuthAsGuestUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.auth.AuthUserUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.auth.CreateSessionUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.impl.auth.GetRequestTokenUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AuthBinds {

    @Binds
    abstract fun bindAuthAsGuestUseCase(authAsGuestUseCase: AuthAsGuestUseCaseImpl): AuthAsGuestUseCase

    @Binds
    abstract fun bindAuthAsUserUseCase(authUserUseCase: AuthUserUseCaseImpl): AuthUserUseCase

    @Binds
    abstract fun bindGetRequestTokenUseCase(getRequestTokenUseCase: GetRequestTokenUseCaseImpl): GetRequestTokenUseCase

    @Binds
    abstract fun bindCreateSessionUseCase(createSessionUseCase: CreateSessionUseCaseImpl): CreateSessionUseCase
}