package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.entity.UserEntity
import com.artelsv.petprojectsecond.data.network.model.auth.AvatarResponse
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import com.artelsv.petprojectsecond.domain.model.Avatar
import com.artelsv.petprojectsecond.domain.model.Gravatar
import com.artelsv.petprojectsecond.domain.model.Tmdb
import com.artelsv.petprojectsecond.domain.model.User

object UserMapper {

    fun userResponseToUser(user: UserResponse) = User(
        avatarResponseToAvatar(user.avatar),
        user.id,
        user.isoLang,
        user.isoCountry,
        user.name,
        user.includeAdult,
        user.username
    )

    fun userToEntity(user: User) = UserEntity(
        uid = 0,
        user.id,
        user.avatar,
        user.isoLang,
        user.isoCountry,
        user.name,
        user.includeAdult,
        user.username
    )

    fun entityToUser(user: UserEntity?) = if (user != null) User(
        user.avatar,
        user.id,
        user.isoLang,
        user.isoCountry,
        user.name,
        user.includeAdult,
        user.username
    ) else null

    private fun avatarResponseToAvatar(avatar: AvatarResponse) = Avatar(Gravatar(avatar.gravatar.hash), Tmdb(avatar.tmdb.avatarPath))
}