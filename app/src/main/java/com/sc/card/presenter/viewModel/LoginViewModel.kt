package com.sc.card.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.card.domain.mapper.toUserEntity
import com.sc.card.domain.usecase.LoginUseCase
import com.sc.card.presenter.extension.toUserModel
import com.sc.card.presenter.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase
): ViewModel() {

    fun doLogin(
        email: String,
        password:String,
        onSuccess: () -> Unit,
        onCredential: () -> Unit,
        onError: () -> Unit
    ){
        viewModelScope.launch {
            useCase.login(
                UserState(
                    email = email,
                    password = password
                ).toUserModel()
            ) { model ->
                val response = model.toUserEntity()
                if (response.email.isNotEmpty()) {
                    onSuccess()
                } else {
                    if (response.password.isNotEmpty()) {
                        onError()
                    } else {
                        onCredential()
                    }
                }
            }
        }
    }
}