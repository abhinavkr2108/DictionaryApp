package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.domain.models.WordInfo

data class WordInfoDTO(
    val meanings: List<MeaningDTO>,
    val phonetic: String,
    val phonetics: List<PhoneticDTO>,
    val sourceUrls: List<String>,
    val word: String
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }

    fun toWordInfoEntity(): WordInfoEntity{
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}