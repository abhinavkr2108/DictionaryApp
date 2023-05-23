package com.example.dictionaryapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.domain.use_case.GetWordInfoUseCase
import com.example.dictionaryapp.presentation.states.WordInfoStates
import com.example.dictionaryapp.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(private val getWordInfoUseCase: GetWordInfoUseCase): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _state = MutableStateFlow(WordInfoStates())
    val state: StateFlow<WordInfoStates> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String){
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            getWordInfoUseCase(query).onEach {
                when(it){
                    is ResponseState.Loading ->{
                        _state.value = state.value.copy(
                            wordInfoItems = it.data?: emptyList(),
                            isLoading = true
                        )
                    }
                    is ResponseState.Error ->{
                        _state.value = state.value.copy(
                            wordInfoItems = it.data?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar(it.message ?: "Unknown Error Occurred"))
                    }
                    is ResponseState.Success ->{
                        _state.value = state.value.copy(
                            wordInfoItems = it.data?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UIEvent{
        data class ShowSnackbar(val message: String): UIEvent()
    }

}