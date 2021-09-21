package com.artelsv.petprojectsecond.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artelsv.petprojectsecond.data.database.dao.MovieDao
import com.artelsv.petprojectsecond.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 8)
@TypeConverters(DataConverter::class)
abstract class MoviesDatabase : RoomDatabase(){

    abstract fun getMovieDao(): MovieDao
}