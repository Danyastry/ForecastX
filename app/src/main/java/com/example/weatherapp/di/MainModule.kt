package com.example.weatherapp.di

import com.example.weatherapp.data.network.Api
import com.example.weatherapp.data.network.HeaderInterceptor
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
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

    single { get<Retrofit>().create(Api::class.java) }

    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

    single { FirebaseAuth.getInstance() }
}
