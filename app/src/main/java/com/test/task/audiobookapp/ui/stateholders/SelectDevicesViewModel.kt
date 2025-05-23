package com.test.task.audiobookapp.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.task.audiobookapp.data.model.DeviceType
import com.test.task.audiobookapp.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectDevicesViewModel(val deviceRepository: DeviceRepository): ViewModel() {
    private val _selectedTab = MutableStateFlow(DeviceType.IPHONE)
    val selectedTab = _selectedTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectionMode = MutableStateFlow(true)
    val selectionMode = _selectionMode.asStateFlow()

    private val _selectedDevices = MutableStateFlow<Set<Int>>(emptySet())
    val selectedDevices = _selectedDevices.asStateFlow()

    val devices = combine(
        deviceRepository.getDevices(),
        _selectedTab,
        _searchQuery
    ) { devices, tab, query ->
        devices
            .filter { it.type == tab }
            .filter {
                if (query.isBlank()) true
                else it.label.contains(query, ignoreCase = true)
            }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(DELAY_TIME),
        emptyList()
    )

    val selectedDevicesCount = _selectedDevices.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(DELAY_TIME), 0)

    fun setTab(tab: DeviceType) {
        _selectedTab.value = tab
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSelectionMode() {
        _selectionMode.value = !_selectionMode.value
        if (!_selectionMode.value) {
            _selectedDevices.value = emptySet()
        }
    }

    fun toggleDeviceSelection(deviceId: Int) {
        _selectedDevices.update { selected ->
            if (selected.contains(deviceId)) {
                selected - deviceId
            } else {
                selected + deviceId
            }
        }
    }

    fun selectAllDevices() {
        viewModelScope.launch {
            devices.value.map { it.id }.toSet().also {
                _selectedDevices.value = it
            }
        }
    }

    fun resetSelectedDevices() {
        viewModelScope.launch {
            _selectedDevices.value.forEach { deviceId ->
                deviceRepository.resetDevice(deviceId)
            }
            _selectedDevices.value = emptySet()
        }
    }

    fun resetDevice(deviceId: Int) {
        viewModelScope.launch {
            val deviceExists = deviceRepository.resetDevice(deviceId)
            if (deviceExists) {
                _selectedDevices.update { selected ->
                    selected - deviceId
                }
            }
        }
    }
}