package com.example.dictionaryapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapp.DictionaryApp
import com.example.dictionaryapp.data.DictionaryApi
import com.example.dictionaryapp.data.local.Converters
import com.example.dictionaryapp.data.local.WordInfoDao
import com.example.dictionaryapp.data.local.entity.WordInfoDatabase
import com.example.dictionaryapp.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.data.util.GsonParser
import com.example.dictionaryapp.domain.repository.WordInfoRepository
import com.example.dictionaryapp.domain.use_case.GetWordInfoUseCase
import com.example.dictionaryapp.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUsecase(repository: WordInfoRepository): GetWordInfoUseCase{
        return GetWordInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(api: DictionaryApi, db: WordInfoDatabase): WordInfoRepository{
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase{
        return Room.databaseBuilder(app, WordInfoDatabase::class.java, "word_db")
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}