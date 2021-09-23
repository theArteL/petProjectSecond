package com.artelsv.petprojectsecond.di.module

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.data.database.MoviesDatabase
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.*
import com.artelsv.petprojectsecond.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
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

    // binds
    @Provides
    fun provideMoviesDao(db: MoviesDatabase) = db.getMovieDao()

    @Provides
    fun providesMoviesRepository(moviesDao: MovieDao, moviesService: MoviesService): MoviesRepository = MoviesRepositoryImpl(moviesDao, moviesService)

    // usesCases

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository): GetPopularMoviesUseCase = GetPopularMoviesUseCaseImpl(moviesRepository)

    @Provides
    fun provideGetNowPlayingMoviesUseCase(moviesRepository: MoviesRepository): GetNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCaseImpl(moviesRepository)

    @Provides
    fun provideGetMovieDetailsUseCase(moviesRepository: MoviesRepository): GetMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(moviesRepository)
}