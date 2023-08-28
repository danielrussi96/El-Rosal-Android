package com.app.elrosal.di

import com.app.elrosal.MainViewModel
import com.app.elrosal.data.AndroidPermissionChecker
import com.app.library.shared.cache.DriverFactory
import com.app.library.shared.cache.ElRosalDatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::MainViewModel)
}

val driverModule = module {
    single {
        DriverFactory(get())
    }

    single {
        ElRosalDatabaseFactory(get()).createDatabase()
    }
}

val permissionModule = module {

    factory<AndroidPermissionChecker> {
        AndroidPermissionChecker(androidApplication())
    }
}