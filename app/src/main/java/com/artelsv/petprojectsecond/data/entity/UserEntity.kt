package com.artelsv.petprojectsecond.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artelsv.petprojectsecond.domain.model.Avatar
import com.artelsv.petprojectsecond.utils.Constants

@Entity(tableName = Constants.DATABASE_USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val id: Int,
    val avatar: Avatar,
    val isoLang: String,
    val isoCountry: String,
    val name: String,
    val includeAdult: String,
    val username: String
)