package com.test.task.audiobookapp.domain.model

data class Device(val id: Int,
                  val label: String,
                  val type: DeviceType,
                  val status: DeviceStatus = DeviceStatus.NORMAL)

enum class DeviceType {
    IPHONE, ANDROID
}

enum class DeviceStatus {
    NORMAL, LOST
}
