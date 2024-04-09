package com.sc.card.data.datasource

import com.sc.card.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun registerUser(user: UserEntity) : Flow<Boolean>
    suspend fun login(user: UserEntity, callback: (UserEntity) -> Unit)
}