package com.example.dictionaryapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionaryapp.data.util.JsonParser
import com.example.dictionaryapp.domain.models.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromMeaningJson(json: String): List<Meaning>{
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        )?: emptyList()
    }

    @TypeConverter
    fun toMeaningJsom(meaning: List<Meaning>): String{
        return jsonParser.toJson(meaning, object : TypeToken<ArrayList<Meaning>>(){}.type)?: "[]"
    }
}