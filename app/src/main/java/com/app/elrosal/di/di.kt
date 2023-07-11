package com.app.elrosal.di

import com.app.elrosal.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::MainViewModel)
}