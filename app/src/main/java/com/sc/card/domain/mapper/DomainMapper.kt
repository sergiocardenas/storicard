package com.sc.card.domain.mapper

import com.sc.card.data.entity.UserEntity
import com.sc.card.domain.model.UserModel

fun UserModel.toUserEntity() =
    UserEntity(
        email = this.email,
        name = this.name,
        lastName = this.lastName,
        password = this.password,
        photo = this.photo
    )

fun UserEntity.toUserModel() =
    UserModel(
        email = this.email,
        name = this.name,
        lastName = this.lastName,
        password = this.password,
        photo = this.photo
    )