package com.artelsv.petprojectsecond.di.component

import android.app.Application
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.di.module.*
import com.artelsv.petprojectsecond.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuilder::class,
        AppModule::class,
        NetworkModule::class,
        FragmentBuilder::class,
        AndroidSupportInjectionModule::class,
        NavigatorModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
//    fun inject(activity: MainActivity)
}