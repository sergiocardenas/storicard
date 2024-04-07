package com.sc.card.presenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.sc.card.R
import com.sc.card.databinding.LoginLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        LoginLayoutBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment)
                as NavHostFragment
        navHostFragment.navController

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }



    fun navigate(destination: Int){
        navController.navigate(destination)
    }
}
