package com.test.task.audiobookapp.data

import com.test.task.audiobookapp.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceDataSource {
    fun getDevices(): Flow<List<Device>>
    suspend fun updateDeviceStatus(deviceId: Int, isLost: Boolean): Boolean
}