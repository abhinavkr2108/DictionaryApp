package com.example.dictionaryapp.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.data.local.Converters
import com.example.dictionaryapp.data.local.WordInfoDao

@Database(entities = arrayOf(WordInfoEntity::class), version = 1)

@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {

    abstract val dao: WordInfoDao
}