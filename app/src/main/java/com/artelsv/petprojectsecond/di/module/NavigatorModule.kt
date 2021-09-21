package com.artelsv.petprojectsecond.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigatorModule {

    @Provides
    fun providesCicerone() : Cicerone<Router> = Cicerone.create()

    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
}