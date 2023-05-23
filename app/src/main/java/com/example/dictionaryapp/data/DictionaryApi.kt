package com.example.dictionaryapp.data

import com.example.dictionaryapp.data.remote.dto.WordInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDTO>
}