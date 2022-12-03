package com.example.crm

import android.app.Application
import com.example.crm.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CRMApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@CRMApp)
            modules(applicationModule)
        }
    }
}