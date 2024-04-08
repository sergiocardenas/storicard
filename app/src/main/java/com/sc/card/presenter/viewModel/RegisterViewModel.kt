package com.sc.card.presenter.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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

    private var _userData = MutableStateFlow(UserState())
    val userData: StateFlow<UserState> = _userData

    private var _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    private var _photoString = MutableStateFlow("")
    val photo: StateFlow<String> = _photoString

    private var _photoBitmap = MutableStateFlow<Bitmap?>(null)
    val photoBitmap: StateFlow<Bitmap?> = _photoBitmap

    fun registerUser(user: UserState){
        _userData.value = user.copy(
            photo = _photoString.value
        )
        viewModelScope.launch {
            _success.value = true
        }
    }

    fun saveUserState(user: UserState){
        _userData.value = user.copy(
            photo = _photoString.value
        )
    }

    fun resetViewModel(){
        _success.value = false
        _userData.value = UserState()
        _photoString.value = ""
        _photoBitmap.value = null
    }

    fun setUserPhoto(image64: String){
        _photoString.value = image64
        val decodedString: ByteArray = Base64.decode(image64, Base64.DEFAULT)
        val decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        _photoBitmap.value = decodedBitmap
    }
}