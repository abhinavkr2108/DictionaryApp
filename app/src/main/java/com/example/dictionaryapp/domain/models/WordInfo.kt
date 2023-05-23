package com.example.dictionaryapp.domain.models


data class WordInfo(
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)
