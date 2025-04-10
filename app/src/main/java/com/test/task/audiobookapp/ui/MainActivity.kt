package com.test.task.audiobookapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.test.task.audiobookapp.ui.screens.main.MainScreen
import com.test.task.audiobookapp.ui.theme.AudiobookAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudiobookAppTheme {
                MainScreen()
            }
        }
    }
}

