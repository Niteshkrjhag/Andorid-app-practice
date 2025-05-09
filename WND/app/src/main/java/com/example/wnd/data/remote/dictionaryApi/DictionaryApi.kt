package com.example.wnd.data.remote.dictionaryApi

import com.example.wnd.data.model.dictionaryModel.DictionaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getMeaning(
        @Path("word") word: String
    ): Response<DictionaryResponse>

}