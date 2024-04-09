package com.sc.card.domain.usecase

import com.sc.card.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    suspend fun registerUser(user: UserModel) : Flow<Boolean>
}