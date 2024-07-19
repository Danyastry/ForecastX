package com.example.weatherapp.Repository

import com.example.weatherapp.Models.BaseModel
import com.example.weatherapp.DailyForecast.DailyForecast
import com.example.weatherapp.DailyForecast.Location

interface WeatherRepository{
    suspend fun getLocation(city: String): BaseModel<List<Location>>
    suspend fun getForecast(city: String, days: Int): BaseModel<DailyForecast>
}