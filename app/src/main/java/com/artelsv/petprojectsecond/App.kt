package com.artelsv.petprojectsecond

import com.artelsv.petprojectsecond.di.component.AppComponent
import com.artelsv.petprojectsecond.di.component.DaggerAppComponent
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : DaggerApplication(), HasAndroidInjector {

    lateinit var app: AppComponent

    @Inject
    lateinit var cicerone: Cicerone<Router>
    fun getRouter() = cicerone.router

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().application(this).build()
}
