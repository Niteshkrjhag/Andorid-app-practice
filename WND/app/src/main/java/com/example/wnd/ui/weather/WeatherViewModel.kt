package com.example.wnd.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wnd.data.model.weatherModel.WeatherResponse
import com.example.wnd.data.remote.weatherApi.NetworkResponse
import com.example.wnd.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel(){

    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherResponse>>()
    val weatherResult: LiveData<NetworkResponse<WeatherResponse>> = _weatherResult

    fun getWeather(city: String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city)
                if(response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                   _weatherResult.value = NetworkResponse.Failure("Failed to Load Data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Failure("Failed to Load Data")
            }
        }
    }

}