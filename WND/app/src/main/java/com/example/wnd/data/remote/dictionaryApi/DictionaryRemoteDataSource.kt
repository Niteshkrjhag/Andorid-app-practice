package com.example.wnd.data.remote.dictionaryApi

import com.example.wnd.data.model.dictionaryModel.DictionaryResponse
import retrofit2.Response

class DictionaryRemoteDataSource(private val api: DictionaryApi) {
    suspend fun fetchDefination(word: String): Response<DictionaryResponse>{
       return api.getMeaning(word)
    }
}