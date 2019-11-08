package com.example.marvelworld

import android.app.Application
import com.example.marvelworld.utility.VerificationUtils
import com.example.marvelworld.vm.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication: Application() {

    private val appModule = module {
        single { VerificationUtils() }
    }

    private val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        val modulesList = listOf(appModule, viewModelModule)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(modulesList)
        }
    }
}