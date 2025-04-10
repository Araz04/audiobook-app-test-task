package com.test.task.audiobookapp

import android.app.Application
import com.test.task.audiobookapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AudiobookApplication  : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AudiobookApplication)
            modules(appModule)
        }
    }
}