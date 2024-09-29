package com.example.weatherapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.BaseModel
import com.example.weatherapp.data.model.Location
import com.example.weatherapp.domain.repository.WeatherRepository
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