package com.example.wnd.data.remote.newsApi

import com.example.wnd.data.model.newsModel.TopHeadLinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String? = null,
        @Query("apiKey") apiKey: String
    ): Response<TopHeadLinesResponse>

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response<TopHeadLinesResponse>

}