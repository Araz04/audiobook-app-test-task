package com.test.task.audiobookapp.domain.repository

import com.test.task.audiobookapp.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getDevices(): Flow<List<Device>>
    suspend fun markAsLost(deviceId: Int): Boolean
    suspend fun recoverDevice(deviceId: Int): Boolean
    suspend fun resetDevice(deviceId: Int): Boolean
    suspend fun searchDevices(query: String): Flow<List<Device>>
}