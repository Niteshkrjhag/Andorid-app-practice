package com.example.wnd.data.remote.weatherApi

import com.example.wnd.Constant
import com.example.wnd.data.model.weatherModel.WeatherResponse
import retrofit2.Response

class WeatherRemoteDataSource(private val weatherApi: WeatherApi) {
    suspend fun fetchWeather(city: String): Response<WeatherResponse>{
        return weatherApi.getWeather(Constant.weatherApiKey,city)
    }
}