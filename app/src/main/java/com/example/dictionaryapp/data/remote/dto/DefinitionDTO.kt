package com.example.dictionaryapp.data.remote.dto

import com.example.dictionaryapp.domain.models.Definition

data class DefinitionDTO(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
){
    fun toDefinition(): Definition{
        return Definition(
            antonyms = antonyms,
            definition = definition,
            example = example,
            synonyms = synonyms
        )
    }
}