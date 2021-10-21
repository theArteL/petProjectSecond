package com.artelsv.petprojectsecond.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.data.database.MoviesDatabase
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.datasource.*
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.data.repository.UserRepositoryImpl
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.*
import com.artelsv.petprojectsecond.domain.usecases.impl.*
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
@Module(includes = [ViewModelModule::class, NetworkModule::class, AppModule.DataSourcesBinds::class, AppModule.UseCasesBinds::class])
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
    fun provideObscuredSharedPreference(context: Context, pref: SharedPreferences): ObscuredSharedPreferences {
        return ObscuredSharedPreferences(context, pref)
    }

    @Provides
    fun providesMoviesDao(db: MoviesDatabase): MovieDao = db.getMovieDao()

    @ExperimentalCoroutinesApi
    @Provides
    fun providesMoviesRepository(
        @Named("movieLocalDataSource") localDataSource: MovieDataSource,
        @Named("movieRemoteDataSource") remoteDataSource: MovieDataSource,
        nowPlayingMoviePagingSource: NowPlayingMoviePagingSource.Factory,
        popularMoviePagingSource: PopularMoviePagingSource.Factory
    ): MoviesRepository = MoviesRepositoryImpl(
        localDataSource,
        remoteDataSource,
        nowPlayingMoviePagingSource,
        popularMoviePagingSource
    )

    @Provides
    fun providesUserRepository(
        @Named("userRemoteDataSource") userRemoteDataSource: UserDataSource,
        pref: ObscuredSharedPreferences
    ): UserRepository = UserRepositoryImpl(userRemoteDataSource, SharedPreferenceManager(pref))

    @Module
    abstract class DataSourcesBinds {

        @Binds
        @Named("movieLocalDataSource")
        abstract fun bindLocalDataSource(localDataSource: MovieLocalDataSource): MovieDataSource

        @Binds
        @Named("movieRemoteDataSource")
        abstract fun bindRemoteDataSource(remoteDataSource: MovieRemoteDataSource): MovieDataSource

        @Binds
        @Named("userRemoteDataSource")
        abstract fun bindUserRemoteDataSource(userRemoteDataSource: UserRemoteDataSource): UserDataSource
    }

    @Module
    abstract class UseCasesBinds {

        @Binds
        abstract fun bindGetPopularMoviesUseCase(getPopularMoviesUseCaseImpl: GetPopularMoviesUseCaseImpl): GetPopularMoviesUseCase

        @Binds
        abstract fun bindGetNowPlayingMoviesUseCase(getNowPlayingMoviesUseCaseImpl: GetNowPlayingMoviesUseCaseImpl): GetNowPlayingMoviesUseCase

        @Binds
        abstract fun bindGetMovieDetailsUseCase(getMovieDetailsUseCaseImpl: GetMovieDetailsUseCaseImpl): GetMovieDetailsUseCase

        @Binds
        abstract fun bindGetMovieDateReleaseUseCase(getMovieDetailsUseCaseImpl: GetMovieDateReleaseUseCaseImpl): GetMovieDateReleaseUseCase

        @Binds
        abstract fun bindAuthAsGuestUseCase(authAsGuestUseCase: AuthAsGuestUseCaseImpl): AuthAsGuestUseCase

        @Binds
        abstract fun bindAuthAsUserUseCase(authUserUseCase: AuthUserUseCaseImpl): AuthUserUseCase

        @Binds
        abstract fun bindGetRequestTokenUseCase(getRequestTokenUseCase: GetRequestTokenUseCaseImpl): GetRequestTokenUseCase

        @Binds
        abstract fun bindCreateSessionUseCase(createSessionUseCase: CreateSessionUseCaseImpl): CreateSessionUseCase

        @Binds
        abstract fun bindGetUserUseCase(getUserUseCase: GetUserUseCaseImpl): GetUserUseCase

        @Binds
        abstract fun bindUserListsUseCase(userListsUseCase: UserListsUseCaseImpl): UserListsUseCase

        @Binds
        abstract fun bindMarkAsFavoriteUseCase(markAsFavoriteUseCase: MarkAsFavoriteUseCaseImpl): MarkAsFavoriteUseCase
    }
}