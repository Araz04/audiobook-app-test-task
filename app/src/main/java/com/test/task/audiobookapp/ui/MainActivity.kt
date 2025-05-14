package com.test.task.audiobookapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.test.task.audiobookapp.ui.screens.main.MainScreen
import com.test.task.audiobookapp.ui.stateholders.MainViewModel
import com.test.task.audiobookapp.ui.theme.AudiobookAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

const val MAXIMUM_SELECTED_ITEMS = 5

class MainActivity : ComponentActivity() {
    val pickMultipleMedia =
        registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(
                MAXIMUM_SELECTED_ITEMS
            )
        ) { uris ->
            if (uris.isNotEmpty()) {
                mainViewModel.updateSelectedImages(uris.toSet())
            }
        }

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudiobookAppTheme {
                MainScreen(mainViewModel = mainViewModel, onPickImage = {
                    pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                )
            }
        }
    }
}

