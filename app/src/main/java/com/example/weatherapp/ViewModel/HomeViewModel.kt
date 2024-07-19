package com.example.weatherapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Models.BaseModel
import com.example.weatherapp.DailyForecast.Location
import com.example.weatherapp.Repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    val repository: WeatherRepository by inject()

    private val _locations: MutableStateFlow<BaseModel<List<Location>>?> = MutableStateFlow(null)
    val locations = _locations.asStateFlow()

    fun getLocation(city: String) {
        viewModelScope.launch {
            _locations.update { BaseModel.Loading }
            repository.getLocation(city).also { data ->
                _locations.update { data }
            }
        }
    }
}