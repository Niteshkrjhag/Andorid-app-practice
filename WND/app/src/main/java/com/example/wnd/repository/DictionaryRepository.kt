package com.example.wnd.repository

import com.example.wnd.data.model.dictionaryModel.DictionaryResponse
import com.example.wnd.data.remote.dictionaryApi.DictionaryRemoteDataSource
import retrofit2.Response

class DictionaryRepository(private val dictionaryRemoteDataSource: DictionaryRemoteDataSource) {
    suspend fun getDefination(word: String): Response<DictionaryResponse> {
        return dictionaryRemoteDataSource.fetchDefination(word)
    }
}