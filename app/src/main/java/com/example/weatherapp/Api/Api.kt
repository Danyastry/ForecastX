package com.example.weatherapp.Api

import com.example.weatherapp.DailyForecast.DailyForecast
import com.example.weatherapp.DailyForecast.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "59c3f61f89944e20b59174254241602"

interface Api {
    @GET("/v1/search.json")
    suspend fun getLocation(
        @Query("key") apikey: String = API_KEY,
        @Query("q") city: String
    ): Response<List<Location>>

    @GET("/v1/forecast.json")
    suspend fun getForecast(
        @Query("key") apikey: String = API_KEY,
        @Query("q") city: String,
        @Query("days") days: Int
    ): Response<DailyForecast>

}