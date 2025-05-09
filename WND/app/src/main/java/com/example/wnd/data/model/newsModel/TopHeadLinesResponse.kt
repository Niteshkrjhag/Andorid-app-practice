package com.example.wnd.data.model.newsModel

data class TopHeadLinesResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)