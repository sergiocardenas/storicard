package com.sc.card.presenter.extension

import com.sc.card.domain.model.UserModel
import com.sc.card.presenter.state.UserState

fun UserModel.toUserState() =
    UserState(
        email = this.email,
        name = this.name,
        lastName = this.lastName,
        password = this.password,
        photo = this.photo
    )

fun UserState.toUserModel() =
    UserModel(
        email = this.email,
        name = this.name,
        lastName = this.lastName,
        password = this.password,
        photo = this.photo
    )