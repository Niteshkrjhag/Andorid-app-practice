package com.example.wnd.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wnd.data.remote.dictionaryApi.DictionaryApi
import com.example.wnd.data.remote.dictionaryApi.DictionaryRemoteDataSource
import com.example.wnd.data.remote.newsApi.NewsApi
import com.example.wnd.data.remote.newsApi.NewsRemoteDataSource
import com.example.wnd.data.remote.weatherApi.WeatherApi
import com.example.wnd.data.remote.weatherApi.WeatherRemoteDataSource
import com.example.wnd.repository.DictionaryRepository
import com.example.wnd.repository.NewsRepository
import com.example.wnd.repository.WeatherRepository
import com.example.wnd.ui.dictionary.DictionaryViewModel
import com.example.wnd.ui.news.NewsViewModel
import com.example.wnd.ui.weather.WeatherViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
     private val weatherRetrofit = Retrofit.Builder()
         .baseUrl("https://api.weatherapi.com")
         .addConverterFactory(GsonConverterFactory.create())
         .build()

    private val weatherApi = weatherRetrofit.create(WeatherApi::class.java)
    private val weatherRemoteDataSource = WeatherRemoteDataSource(weatherApi)
    private val weatherRepository = WeatherRepository(weatherRemoteDataSource)

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("User-Agent", "WND/1.0")  // Add user-agent or any other header
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val newsRetrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val newsApi = newsRetrofit.create(NewsApi::class.java)
    private val newsRemoteDataSource = NewsRemoteDataSource(newsApi)
    private val newsRepository = NewsRepository(newsRemoteDataSource)

    private val dictionaryRetrofit = Retrofit.Builder()
        .baseUrl("https://api.dictionaryapi.dev")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val dictionaryApi = dictionaryRetrofit.create(DictionaryApi::class.java)
    private val dictionaryRemoteDataSource = DictionaryRemoteDataSource(dictionaryApi)
    private val dictionaryRepository = DictionaryRepository(dictionaryRemoteDataSource)



    val viewModelFactory = object:ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when(modelClass){
               WeatherViewModel::class.java -> WeatherViewModel(weatherRepository)
               NewsViewModel::class.java-> NewsViewModel(newsRepository)
                DictionaryViewModel::class.java -> DictionaryViewModel(dictionaryRepository)
                else -> throw IllegalArgumentException("Unknown ViewModel")
            } as T
        }
    }
}