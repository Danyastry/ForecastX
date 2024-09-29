package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.mainModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            modules(mainModule)
        }
    }
}