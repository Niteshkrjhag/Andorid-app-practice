package com.example.wnd.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wnd.data.model.newsModel.TopHeadLinesResponse
import com.example.wnd.data.remote.weatherApi.NetworkResponse
import com.example.wnd.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val respository: NewsRepository): ViewModel() {
    private val _topHeadlinesResult = MutableLiveData<NetworkResponse<TopHeadLinesResponse>>()
    val topHeadLinesResult: LiveData<NetworkResponse<TopHeadLinesResponse>> = _topHeadlinesResult

    init {
        getTopHeadLines()
    }

    fun getTopHeadLines(country: String = "us",category: String? = null){
        _topHeadlinesResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response =respository.getTopheadLines(country, category = category)
                Log.e("Response", "Error: ${response.code()} - ${response.message()}")
                Log.e("API_ERROR", "Code: ${response.code()}, Message: ${response.message()}, Body: ${response.errorBody()?.string()}")
                if(response.isSuccessful){
                    Log.e("Response"," Success: ${response.body()?.articles}")
                   response.body()?.let {
                       _topHeadlinesResult.value = NetworkResponse.Success(it)
                   }
                }else{
                    Log.e("Response"," Inside Else: ${response.body()?.articles}")
                    _topHeadlinesResult.value = NetworkResponse.Failure("unable to load news")
                }
            }
            catch (
                e: Exception
            ){
                _topHeadlinesResult.value = NetworkResponse.Failure("Unable to load news")
            }
        }
    }
    fun getEverything(query: String){
        _topHeadlinesResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = respository.getEverything(query)
                if(response.isSuccessful){
                    response.body()?.let {
                        _topHeadlinesResult.value = NetworkResponse.Success(it)
                    }
                }else{
                   _topHeadlinesResult.value =  NetworkResponse.Failure("Not Loaded")
                }
            }catch (
                e: Exception
            ){
                    _topHeadlinesResult.value = NetworkResponse.Failure("Not Loaded")
            }
        }
    }
}