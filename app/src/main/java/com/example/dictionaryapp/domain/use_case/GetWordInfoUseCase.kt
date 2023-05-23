package com.example.dictionaryapp.domain.use_case

import com.example.dictionaryapp.domain.models.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepository
import com.example.dictionaryapp.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCase(private val repository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<ResponseState<List<WordInfo>>> {

        if (word.isEmpty() or word.isBlank()){
            return flow {  }
        }

        return repository.getWordInfo(word)
    }
}