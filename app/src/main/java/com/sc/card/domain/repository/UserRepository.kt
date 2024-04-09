package com.sc.card.domain.repository

import com.sc.card.data.datasource.RemoteDataSource
import com.sc.card.domain.mapper.toUserEntity
import com.sc.card.domain.mapper.toUserModel
import com.sc.card.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remote : RemoteDataSource
) {
    suspend fun registerUser(user: UserModel) : Flow<Boolean> =
        remote.registerUser(user.toUserEntity())
    suspend fun login(user: UserModel, callback: (UserModel) -> Unit) =
        remote.login(user.toUserEntity()){ userEntity ->
            callback(userEntity.toUserModel())
        }
}