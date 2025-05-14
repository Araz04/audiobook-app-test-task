package com.test.task.audiobookapp.di

import com.test.task.audiobookapp.data.datasource.DeviceDataSource
import com.test.task.audiobookapp.data.repository.DeviceRepositoryImpl
import com.test.task.audiobookapp.domain.repository.DeviceRepository
import com.test.task.audiobookapp.ui.stateholders.HomeViewModel
import com.test.task.audiobookapp.ui.stateholders.MainViewModel
import com.test.task.audiobookapp.ui.stateholders.SelectDevicesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DeviceDataSource() }

    single<DeviceRepository> { DeviceRepositoryImpl(get()) }

    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { SelectDevicesViewModel(get()) }
}