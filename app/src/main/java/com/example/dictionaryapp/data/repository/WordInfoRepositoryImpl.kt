package com.example.dictionaryapp.data.repository

import com.example.dictionaryapp.data.DictionaryApi
import com.example.dictionaryapp.data.local.WordInfoDao
import com.example.dictionaryapp.domain.models.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepository
import com.example.dictionaryapp.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository{
    override fun getWordInfo(word: String): Flow<ResponseState<List<WordInfo>>> = flow{

        emit(ResponseState.Loading())
        val wordInfo = dao.getWordInfo(word).map { it.toWordInfo() }
//        emit(ResponseState.Loading(data = wordInfo))

        try {
            val remoteWordInfo = api.getWordInfo(word)
            dao.deleteWordInfo(remoteWordInfo.map { it.word })
            dao.insertWordInfo(remoteWordInfo.map { it.toWordInfoEntity() })
        } catch (e: HttpException){
            emit(ResponseState.Error(
                message = "Something Went Wrong: ${e.localizedMessage}",
                data = wordInfo
            ))

        } catch (e: IOException){
            emit(ResponseState.Error(
                message = "Couldn't reach server: ${e.localizedMessage}",
                data = wordInfo
            ))
        }

        val newWordInfo = dao.getWordInfo(word).map { it.toWordInfo() }
        emit(ResponseState.Success(data = newWordInfo))
    }
}