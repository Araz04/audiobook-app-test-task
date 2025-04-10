package com.test.task.audiobookapp.domain.repository

import com.test.task.audiobookapp.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevices(): Flow<List<Device>>
    suspend fun markAsLost(deviceId: String): Boolean
    suspend fun recoverDevice(deviceId: String): Boolean
    suspend fun resetDevice(deviceId: String): Boolean
    suspend fun searchDevices(query: String): Flow<List<Device>>
}