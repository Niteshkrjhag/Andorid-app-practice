package com.example.wnd.repository

import com.example.wnd.data.model.weatherModel.WeatherResponse
import com.example.wnd.data.remote.weatherApi.WeatherRemoteDataSource
import retrofit2.Response

class WeatherRepository(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) {
    suspend fun getWeather(city: String): Response<WeatherResponse> {
        return weatherRemoteDataSource.fetchWeather(city)
    }
}