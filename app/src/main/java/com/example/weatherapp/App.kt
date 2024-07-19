package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.Api.Api
import com.example.weatherapp.Api.HeaderInterceptor
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.Repository.WeatherRepositoryImpl
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            modules(
                module {
                    single {
                        val client = OkHttpClient.Builder()
                            .addInterceptor(HeaderInterceptor())
                            .build()

                        Retrofit.Builder()
                            .baseUrl("https://api.weatherapi.com")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                    single {
                        val retrofit: Retrofit = get()
                        retrofit.create(Api::class.java)
                    }
                    single {
                        val api: Api = get()
                        WeatherRepositoryImpl(api)
                    } bind WeatherRepository::class
                    single {
                        FirebaseAuth.getInstance()
                    }
                }
            )
        }
    }
}