package com.example.weatherapp.Details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.weatherapp.DailyForecast.Hour
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun WeatherForecastHourly(forecast: List<Hour>) {
    val currentTime = System.currentTimeMillis()
    val currentTimeForecast = forecast.filter {
        val hourTimeMillis =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(it.time)?.time ?: 0
        hourTimeMillis >= currentTime
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(currentTimeForecast) { hour ->
            HourlyForecastItem(hour)
        }
    }
}