package com.sc.card.domain.usecase

import com.sc.card.domain.model.UserModel
import com.sc.card.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCaseImp @Inject constructor(
    private val repository: UserRepository
) : RegisterUseCase {
    override suspend fun registerUser(user: UserModel): Flow<Boolean> =
        repository.registerUser(user)

}