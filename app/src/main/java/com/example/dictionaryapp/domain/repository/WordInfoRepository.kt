package com.example.dictionaryapp.domain.repository

import com.example.dictionaryapp.domain.models.WordInfo
import com.example.dictionaryapp.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<ResponseState<List<WordInfo>>>
}