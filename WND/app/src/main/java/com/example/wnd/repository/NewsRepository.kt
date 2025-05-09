package com.example.wnd.repository

import com.example.wnd.data.model.newsModel.TopHeadLinesResponse
import com.example.wnd.data.remote.newsApi.NewsRemoteDataSource
import retrofit2.Response

class NewsRepository(private val newsRemoteDataSource: NewsRemoteDataSource) {
    suspend fun getTopheadLines(country: String,category: String?): Response<TopHeadLinesResponse>{
        return newsRemoteDataSource.fetechTopHeadLines(
            country = country,
            category = category
        )
    }
    suspend fun getEverything(
        query: String
    ): Response<TopHeadLinesResponse>{
        return newsRemoteDataSource.fetchEverything(query = query)
    }
}