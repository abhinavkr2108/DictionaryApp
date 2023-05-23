package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.models.Meaning

data class MeaningDTO(
    val antonyms: List<String>,
    val definitions: List<DefinitionDTO>,
    val partOfSpeech: String,
    val synonyms: List<String>
){
    fun toMeaning(): Meaning{
        return Meaning(
            antonyms = antonyms,
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech,
            synonyms = synonyms
        )
    }
}