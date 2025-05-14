package com.test.task.audiobookapp.ui

import android.os.Bundle
import android.util.Log
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
    val mainViewModel: MainViewModel by viewModel()

    val pickMultipleMedia =
        registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(
                MAXIMUM_SELECTED_ITEMS
            )
        ) { uris ->
            if (uris.isNotEmpty()) {
                mainViewModel.updateSelectedUris(uris.toSet())
            }
        }

    private val pickMultipleDocuments =
        registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris ->
            if (uris.isNotEmpty()) {
                Log.d("DocumentPicker", "Number of documents selected: ${uris.size}")
                // Update the ViewModel with the selected document URIs
                mainViewModel.updateSelectedUris(uris.toSet())
            } else {
                Log.d("DocumentPicker", "No documents selected")
            }
        }

    private val documentMimeTypes = arrayOf(
        "application/pdf", // PDF files
        "application/msword", // DOC files
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // DOCX files
        "application/vnd.ms-excel", // XLS files
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // XLSX files
        "text/plain" // TXT files
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudiobookAppTheme {
                MainScreen(
                    mainViewModel = mainViewModel,
                    onPickImages = {
                        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    onPickDocuments = {
                        pickMultipleDocuments.launch(documentMimeTypes)
//                        pickMultipleDocuments.launch(arrayOf("*/*"))
                    }
                )
            }
        }
    }
}

