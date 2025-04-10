package com.test.task.audiobookapp.data.datasource

import com.test.task.audiobookapp.domain.model.Device
import com.test.task.audiobookapp.domain.model.DeviceStatus
import com.test.task.audiobookapp.domain.model.DeviceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DeviceDataSource {
    private val devicesFlow = MutableStateFlow(generateMockDevices())

    fun getDevices(): Flow<List<Device>> = devicesFlow

    fun updateDeviceStatus(deviceId: String, isLost: Boolean): Boolean {
        val newStatus = if (isLost) DeviceStatus.LOST else DeviceStatus.NORMAL

        devicesFlow.update { devices ->
            devices.map { device ->
                if (device.id == deviceId) device.copy(status = newStatus) else device
            }
        }
        return true
    }

    fun resetDevice(deviceId: String): Boolean {
        var wasReset = false
        devicesFlow.update { devices ->
            devices.map { device ->
                if (device.id == deviceId && device.status == DeviceStatus.LOST) {
                    wasReset = true
                    device.copy(status = DeviceStatus.NORMAL)
                } else {
                    device
                }
            }
        }
        return wasReset
    }

    private fun generateMockDevices(): List<Device> {
        return listOf(
            Device("1", "iPhone 13", DeviceType.IPHONE),
            Device("2", "iPhone 14 Pro", DeviceType.IPHONE),
            Device("3", "iPhone 16", DeviceType.IPHONE),
            Device("4", "Pixel 9 pro", DeviceType.ANDROID),
            Device("5", "Samsung S21", DeviceType.ANDROID),
            Device("6", "OnePlus 11", DeviceType.ANDROID),
            Device("7", "iPhone SE", DeviceType.IPHONE),
            Device("8", "Xiaomi Mi 14", DeviceType.ANDROID),
            Device("9", "Samsung S25 Ultra", DeviceType.ANDROID),
            Device("10", "iPhone 13 Mini", DeviceType.IPHONE),
            Device("11", "iPhone 15 Pro Max", DeviceType.IPHONE),
            Device("12", "Pixel 8a", DeviceType.ANDROID),
            Device("13", "Pixel 7 Pro", DeviceType.ANDROID)
        )
    }
}