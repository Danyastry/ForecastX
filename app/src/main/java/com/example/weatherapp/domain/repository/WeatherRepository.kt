package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.BaseModel
import com.example.weatherapp.data.model.DailyForecast
import com.example.weatherapp.data.model.Location

interface WeatherRepository{
    suspend fun getLocation(city: String): BaseModel<List<Location>>
    suspend fun getForecast(city: String, days: Int): BaseModel<DailyForecast>
}