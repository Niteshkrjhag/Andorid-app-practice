package com.example.wnd.data.remote.weatherApi

import com.example.wnd.data.model.weatherModel.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

@GET("/v1/current.json")
suspend fun getWeather(
    @Query("key") weatherApiKey: String,
    @Query("q") city: String
): Response<WeatherResponse>

}