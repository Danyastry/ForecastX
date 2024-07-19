package com.example.weatherapp.DailyForecast

data class DailyForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)