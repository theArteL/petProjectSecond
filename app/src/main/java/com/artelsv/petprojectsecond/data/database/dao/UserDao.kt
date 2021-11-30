package com.artelsv.petprojectsecond.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artelsv.petprojectsecond.data.entity.UserEntity
import com.artelsv.petprojectsecond.data.entity.UserMovieEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: UserEntity)

    @Query("SELECT * FROM users ORDER BY users.uid DESC LIMIT 1")
    fun get(): UserEntity?
}