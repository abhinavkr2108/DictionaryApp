package com.example.dictionaryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.domain.models.Meaning
import com.example.dictionaryapp.domain.models.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey val id: Int? = null,
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            meanings = meanings,
            phonetic = phonetic,
            word = word
        )
    }

}
