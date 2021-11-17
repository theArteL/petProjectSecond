package com.artelsv.petprojectsecond.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.database.dao.UserDao
import com.artelsv.petprojectsecond.data.database.dao.UserMovieDao
import com.artelsv.petprojectsecond.data.entity.MovieEntity
import com.artelsv.petprojectsecond.data.entity.UserEntity
import com.artelsv.petprojectsecond.data.entity.UserMovieEntity

@Database(entities = [MovieEntity::class, UserMovieEntity::class, UserEntity::class], version = 10)
@TypeConverters(DataConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
    abstract fun getUserMovieDao(): UserMovieDao
    abstract fun getUserDao(): UserDao
}