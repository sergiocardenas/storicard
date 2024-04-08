package com.sc.card.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sc.card.R
import com.sc.card.presenter.activity.LoginActivity
import com.sc.card.presenter.extension.validateEmail
import com.sc.card.presenter.screen.RegisterScreen
import com.sc.card.presenter.state.UserState
import com.sc.card.presenter.viewModel.RegisterViewModel

class RegisterFragment: Fragment() {

    private val registerViewModel by lazy {
        ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel.resetViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterClick = ::onRegisterClick,
                onTakePhotoClick = { user ->
                    registerViewModel.saveUserState(user)
                    goToCamera()
                },
                onSuccessClick = ::onRegisterSuccessClick
                )
        }
        return composeView
    }

    private fun onRegisterClick(user: UserState){

        if(
            user.email.isNotEmpty() &&
            user.name.isNotEmpty() &&
            user.lastName.isNotEmpty() &&
            user.password.isNotEmpty() &&
            registerViewModel.photo.value.isNotEmpty()
        ){
            if(!user.email.validateEmail()){
                Toast.makeText(
                    requireContext(),
                    "Enter a valid email",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            registerViewModel.registerUser(user)
        }
    }


    private fun onRegisterSuccessClick(){
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun goToCamera(){
        (activity as LoginActivity).navigate(R.id.nav_register_to_camera)
    }
}