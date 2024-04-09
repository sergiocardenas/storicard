package com.sc.card.domain.usecase

import com.sc.card.domain.model.UserModel

interface LoginUseCase {
    suspend fun login(user: UserModel, callback: (UserModel) -> Unit)
}