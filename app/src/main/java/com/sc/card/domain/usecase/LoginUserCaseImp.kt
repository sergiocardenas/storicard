package com.sc.card.domain.usecase

import com.sc.card.domain.model.UserModel
import com.sc.card.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserCaseImp @Inject constructor(
    private val repository: UserRepository
) : LoginUseCase {
    override suspend fun login(user: UserModel, callback: (UserModel) -> Unit) =
        repository.login(user, callback)
}