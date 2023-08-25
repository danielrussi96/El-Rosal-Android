package com.app.elrosal

import android.app.Application
import com.app.elrosal.di.androidModule
import com.app.elrosal.di.driverModule
import com.app.elrosal.di.permissionModule
import com.app.library.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule() + androidModule + driverModule + permissionModule)
        }
    }
}