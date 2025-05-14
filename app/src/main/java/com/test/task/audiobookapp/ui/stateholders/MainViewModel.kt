package com.test.task.audiobookapp.ui.stateholders

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val _selectedImages = MutableStateFlow<Set<Uri>>(emptySet())
    val selectedImages = _selectedImages.asStateFlow()

    fun updateSelectedImages(uris: Set<Uri>){
        _selectedImages.value = uris
    }
}