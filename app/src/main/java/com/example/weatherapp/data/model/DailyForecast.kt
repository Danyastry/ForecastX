package com.example.weatherapp.data.model

data class DailyForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)