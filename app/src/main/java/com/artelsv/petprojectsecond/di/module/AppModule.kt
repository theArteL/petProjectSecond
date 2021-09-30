package com.artelsv.petprojectsecond.di.module

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.data.database.MoviesDatabase
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.datasource.*
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.*
import com.artelsv.petprojectsecond.utils.Constants.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkModule::class, AppModule.DataSourcesBinds::class, AppModule.UseCasesBinds::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app

    @Singleton
    @Provides
    fun providesRoomDatabase(app: Context): MoviesDatabase = Room.databaseBuilder(app, MoviesDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("RoomDatabase", "onCreate")
            }
        })
        .build()

    @Provides
    fun bindMoviesDao(db: MoviesDatabase): MovieDao = db.getMovieDao()

    @ExperimentalCoroutinesApi
    @Provides
    fun bindMoviesRepository(
        @Named("movieLocalDataSource") localDataSource: MovieDataSource,
        @Named("movieRemoteDataSource") remoteDataSource: MovieDataSource,
        nowPlayingMoviePagingSource: NowPlayingMoviePagingSource.Factory,
        popularMoviePagingSource: PopularMoviePagingSource.Factory
    ): MoviesRepository = MoviesRepositoryImpl(localDataSource, remoteDataSource, nowPlayingMoviePagingSource, popularMoviePagingSource)

    @Module
    abstract class DataSourcesBinds {

        @Binds
        @Named("movieLocalDataSource")
        abstract fun bindLocalDataSource(localDataSource: MovieLocalDataSource): MovieDataSource

        @Binds
        @Named("movieRemoteDataSource")
        abstract fun bindRemoteDataSource(remoteDataSource: MovieRemoteDataSource): MovieDataSource
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
    }
}