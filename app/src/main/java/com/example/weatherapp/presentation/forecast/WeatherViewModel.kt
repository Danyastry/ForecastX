package com.example.weatherapp.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.BaseModel
import com.example.weatherapp.data.model.DailyForecast
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherViewModel : ViewModel(), KoinComponent {
    private val repository: WeatherRepository by inject()

    private val _forecast: MutableStateFlow<BaseModel<DailyForecast>> =
        MutableStateFlow(BaseModel.Loading)
    val forecast = _forecast.asStateFlow()


    fun getForecast(city: String, days: Int) {
        viewModelScope.launch {
            repository.getForecast(city, days).also { data ->
                _forecast.update { data }
            }
        }
    }
}