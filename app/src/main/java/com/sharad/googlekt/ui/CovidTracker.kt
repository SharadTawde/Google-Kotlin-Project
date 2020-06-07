package com.sharad.googlekt.ui

import android.app.Application
import com.sharad.googlekt.utils.networkModule
import com.sharad.googlekt.utils.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CovidTracker : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(
                networkModule,
                viewModelModule
            )
        }
    }
}