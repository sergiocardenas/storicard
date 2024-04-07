package com.sc.card.presenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sc.card.R
import com.sc.card.databinding.LoginLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: LoginLayoutBinding
    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment)
                as NavHostFragment
        navHostFragment.navController

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun navigate(destination: Int){
        navController.navigate(destination)
    }
}
