package com.example.weatherapp.Repository

import com.example.weatherapp.Api.Api
import com.example.weatherapp.Models.BaseModel
import com.example.weatherapp.DailyForecast.DailyForecast
import com.example.weatherapp.DailyForecast.Location
import retrofit2.Response

class WeatherRepositoryImpl(private val api: Api) : WeatherRepository {
    override suspend fun getLocation(city: String): BaseModel<List<Location>> {
        return request {
            api.getLocation(city = city)
        }
    }

    override suspend fun getForecast(city: String, days: Int): BaseModel<DailyForecast> {
        return request {
            api.getForecast(city = city, days = days)
        }
    }


    suspend fun <T> request(request: suspend () -> Response<T>): BaseModel<T> {
        try {
            request().also {
                return if (it.isSuccessful) {
                    BaseModel.Success(it.body()!!)
                } else {
                    BaseModel.Error(it.errorBody()?.string().toString())
                }
            }
        } catch (e: Exception) {
            return BaseModel.Error(e.message.toString())
        }
    }
}