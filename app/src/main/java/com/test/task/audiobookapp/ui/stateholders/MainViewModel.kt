package com.test.task.audiobookapp.ui.stateholders

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val _selectedUris = MutableStateFlow<Set<Uri>>(emptySet())
    val selectedUris = _selectedUris.asStateFlow()

    fun updateSelectedUris(uris: Set<Uri>){
        _selectedUris.value = uris
    }
}