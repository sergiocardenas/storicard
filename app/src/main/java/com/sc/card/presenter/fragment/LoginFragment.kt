package com.sc.card.presenter.fragment

import android.content.Intent
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
import com.sc.card.presenter.activity.SessionActivity
import com.sc.card.presenter.screen.LoginScreen
import com.sc.card.presenter.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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
            LoginScreen(
                ::doLogin,
                ::goToRegister)
        }
        return composeView
    }

    private fun goToRegister(){
        (activity as LoginActivity).navigate(R.id.nav_login_to_register)
    }

    private fun goToSessionActivity(){
        val myIntent = Intent(requireActivity(), SessionActivity::class.java)
        requireActivity().startActivity(myIntent)
        requireActivity().finish()
    }

    private fun doLogin(
        email: String,
        password:String
    ){
        loginViewModel.doLogin(
            email, password,
            {
                goToSessionActivity()
            },
            {
                requireActivity().runOnUiThread {
                    val msg = "User or password not found"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            },
            {
                requireActivity().runOnUiThread {
                    val msg = "User not registered yet"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}