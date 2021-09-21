package com.artelsv.petprojectsecond.di.module

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.database.MoviesDatabase
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val DATABASE_NAME = "movies"

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app

    @Provides
    @Singleton
    fun provideApplication(app: App): Application = app

//    @Provides
//    @Singleton
//    fun provideMoviesService(retrofit: Retrofit): MoviesService =
//        retrofit.create(MoviesService::class.java)

    @Singleton
    @Provides
    fun providesRoomDatabase(app: Application): MoviesDatabase = Room.databaseBuilder(app, MoviesDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("RoomDatabaseModule", "onCreate")
            }
        })
        .build()

    @Provides
    @Singleton
    fun provideMoviesDao(db: MoviesDatabase) = db.getMovieDao()

    @Singleton
    @Provides
    fun providesMoviesRepository(moviesDao: MovieDao, moviesService: MoviesService): MoviesRepository {
        return MoviesRepositoryImpl(moviesDao, moviesService)
    }

//    @Provides
//    fun productViewModel(activity: MainActivity): MoviesViewModel {
//        return ViewModelProvider(activity).get(MoviesViewModel::class.java)
//    }
}