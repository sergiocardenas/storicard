package com.sc.card.presenter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.sc.card.R
import com.sc.card.presenter.screen.SessionScreen
import com.sc.card.ui.theme.CardTheme

class SessionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardTheme {
                SessionScreen()
            }
        }
        setContentView(R.layout.activity_main)
    }
}