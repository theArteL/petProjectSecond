package com.artelsv.petprojectsecond.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artelsv.petprojectsecond.data.entity.MovieEntity
import com.artelsv.petprojectsecond.data.entity.UserMovieEntity

@Dao
interface UserMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: UserMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(movies: List<UserMovieEntity>)

    @Query("SELECT * FROM movies WHERE movies.id == :position")
    fun get(position: Int): MovieEntity
}