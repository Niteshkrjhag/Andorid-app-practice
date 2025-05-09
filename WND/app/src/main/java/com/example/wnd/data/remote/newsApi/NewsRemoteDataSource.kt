package com.example.wnd.data.remote.newsApi

import com.example.wnd.Constant
import com.example.wnd.data.model.newsModel.TopHeadLinesResponse
import retrofit2.Response

class NewsRemoteDataSource(private val newsApi: NewsApi) {
    suspend fun fetechTopHeadLines(
        country: String,
        category: String?
    ): Response<TopHeadLinesResponse>{
        return newsApi.getTopHeadlines(
            country,
            category = category,
            apiKey = Constant.newsApiKey
        )
    }
    suspend fun fetchEverything(
        query: String
    ): Response<TopHeadLinesResponse>{
        return newsApi.getEverything(query = query,apiKey = Constant.newsApiKey)
    }
}