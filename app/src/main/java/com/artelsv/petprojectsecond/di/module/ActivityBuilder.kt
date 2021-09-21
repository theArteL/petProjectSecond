package com.artelsv.petprojectsecond.di.module

import com.artelsv.petprojectsecond.ui.AuthActivity
import com.artelsv.petprojectsecond.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesAuthActivity(): AuthActivity
}