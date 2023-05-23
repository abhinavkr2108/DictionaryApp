package com.example.dictionaryapp.presentation.states

import com.example.dictionaryapp.domain.models.WordInfo

data class WordInfoStates(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)