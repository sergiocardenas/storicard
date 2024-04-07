package com.sc.card.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sc.card.presenter.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
): ViewModel() {
    private var _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    private var _photoString = MutableStateFlow("")
    val photo: StateFlow<String> = _photoString

    fun registerUser(user: UserState){
        viewModelScope.launch {
            _success.value = true
        }
    }
}