package com.sc.card.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sc.card.R
import com.sc.card.presenter.viewModel.LoginViewModel
import com.sc.card.presenter.activity.LoginActivity
import com.sc.card.presenter.screen.LoginScreen

class LoginFragment: Fragment() {

    private val loginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.setContent {
            LoginScreen(loginViewModel::doLogin, ::goToRegister)
        }
        return composeView
    }

    private fun goToRegister(){
        (activity as LoginActivity).navigate(R.id.nav_login_to_register)
    }
}