package com.artelsv.petprojectsecond.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.data.database.MoviesDatabase
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.database.dao.UserDao
import com.artelsv.petprojectsecond.data.database.dao.UserMovieDao
import com.artelsv.petprojectsecond.data.datasource.*
import com.artelsv.petprojectsecond.data.datasource.impl.movies.NowPlayingMoviePagingSource
import com.artelsv.petprojectsecond.data.datasource.impl.movies.PopularMoviePagingSource
import com.artelsv.petprojectsecond.data.repository.AuthRepositoryImpl
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.data.repository.UserMoviesRepositoryImpl
import com.artelsv.petprojectsecond.data.repository.UserRepositoryImpl
import com.artelsv.petprojectsecond.di.module.binds.usecase.AuthBinds
import com.artelsv.petprojectsecond.di.module.binds.DataSourceBinds
import com.artelsv.petprojectsecond.di.module.binds.usecase.MovieDetailBinds
import com.artelsv.petprojectsecond.di.module.binds.usecase.MoviesBinds
import com.artelsv.petprojectsecond.di.module.binds.usecase.UserMoviesBinds
import com.artelsv.petprojectsecond.domain.repository.AuthRepository
import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.domain.repository.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.*
import com.artelsv.petprojectsecond.domain.usecases.impl.*
import com.artelsv.petprojectsecond.domain.usecases.impl.user.GetUserUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.utils.Constants.DATABASE_NAME
import com.artelsv.petprojectsecond.utils.Constants.PREF_NAME
import com.artelsv.petprojectsecond.utils.ObscuredSharedPreferences
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module(includes = [
    ViewModelModule::class, NetworkModule::class,
    AppModule.UseCasesBinds::class,
    DataSourceBinds::class, MovieDetailBinds::class, MoviesBinds::class, AuthBinds::class, UserMoviesBinds::class
])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app

    @Singleton
    @Provides
    fun providesRoomDatabase(app: Context): MoviesDatabase =
        Room.databaseBuilder(app, MoviesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Timber.tag("RoomDatabase").d("onCreate")
                }
            })
            .build()

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideObscuredSharedPreference(
        context: Context,
        pref: SharedPreferences,
    ): ObscuredSharedPreferences {
        return ObscuredSharedPreferences(context, pref)
    }

    @Provides
    fun providesMoviesDao(db: MoviesDatabase): MovieDao = db.getMovieDao()

    @Provides
    fun providesUserMovieDao(db: MoviesDatabase): UserMovieDao = db.getUserMovieDao()

    @Provides
    fun providesUserDao(db: MoviesDatabase): UserDao = db.getUserDao()

    @ExperimentalCoroutinesApi
    @Provides
    fun providesMoviesRepository(
        @Named("movieLocalDataSource") localDataSource: MovieDataSource,
        @Named("movieRemoteDataSource") remoteDataSource: MovieDataSource,
        @Named("userLocalDataSource") userLocalDataSource: UserLocalDataSource,
        nowPlayingMoviePagingSource: NowPlayingMoviePagingSource.Factory,
        popularMoviePagingSource: PopularMoviePagingSource.Factory,
    ): MoviesRepository = MoviesRepositoryImpl(
        localDataSource,
        remoteDataSource,
        userLocalDataSource,
        nowPlayingMoviePagingSource,
        popularMoviePagingSource
    )

    @Provides
    fun providesUserRepository(
        @Named("userRemoteDataSource") userRemoteDataSource: UserDataSource,
        @Named("userLocalDataSource") userLocalDataSource: UserLocalDataSource,
        pref: ObscuredSharedPreferences,
    ): UserRepository =
        UserRepositoryImpl(userRemoteDataSource, userLocalDataSource, SharedPreferenceManager(pref))

    @Provides
    fun providesAuthRepository(
        @Named("authRemoteDataSource") authRemoteDataSource: AuthDataSource,
        pref: ObscuredSharedPreferences,
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSource, SharedPreferenceManager(pref))

    @Provides
    fun providesUserMoviesRepository(
        @Named("userMovieDataSource") userMovieDataSource: UserMoviesSource,
        @Named("userLocalDataSource") userLocalDataSource: UserLocalDataSource,
        pref: ObscuredSharedPreferences,
    ): UserMoviesRepository = UserMoviesRepositoryImpl(userMovieDataSource, userLocalDataSource, SharedPreferenceManager(pref))

    @Module
    abstract class UseCasesBinds {

        @Binds
        abstract fun bindGetUserUseCase(getUserUseCase: GetUserUseCaseImpl): GetUserUseCase

        @Binds
        abstract fun bindPersonDetailUseCase(personDetailUseCase: PersonDetailUseCaseImpl): PersonDetailUseCase

    }
}