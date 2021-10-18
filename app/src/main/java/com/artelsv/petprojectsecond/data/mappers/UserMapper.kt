package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.auth.AvatarResponse
import com.artelsv.petprojectsecond.data.network.model.auth.UserResponse
import com.artelsv.petprojectsecond.domain.model.Avatar
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

    private fun avatarResponseToAvatar(avatar: AvatarResponse) = Avatar(avatar.hash)
}