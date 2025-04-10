package com.test.task.audiobookapp.data.repository

import com.test.task.audiobookapp.data.datasource.DeviceDataSource
import com.test.task.audiobookapp.domain.model.Device
import com.test.task.audiobookapp.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeviceRepositoryImpl(private val deviceDataSource: DeviceDataSource): DeviceRepository {
    override fun getDevices(): Flow<List<Device>> = deviceDataSource.getDevices()

    override suspend fun markAsLost(deviceId: String): Boolean =
        deviceDataSource.updateDeviceStatus(deviceId, true)

    override suspend fun recoverDevice(deviceId: String): Boolean =
        deviceDataSource.updateDeviceStatus(deviceId, false)

    override suspend fun resetDevice(deviceId: String): Boolean =
        deviceDataSource.resetDevice(deviceId)

    override suspend fun searchDevices(query: String): Flow<List<Device>> {
        return deviceDataSource.getDevices().map { devices ->
            devices.filter { it.label.contains(query, ignoreCase = true) }
        }
    }
}