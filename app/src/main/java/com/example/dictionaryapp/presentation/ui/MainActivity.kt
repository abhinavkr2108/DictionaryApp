package com.example.dictionaryapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionaryapp.presentation.viewmodels.WordInfoViewModel
import com.example.dictionaryapp.ui.theme.DictionaryAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryAppTheme {
//               val viewModel: WordInfoViewModel by viewModels<WordInfoViewModel>()
                val viewModel: WordInfoViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                val scaffoldState = rememberScaffoldState()
                
                LaunchedEffect(key1 = true){
                    viewModel.eventFlow.collectLatest {
                        when(it) {
                            is WordInfoViewModel.UIEvent.ShowSnackbar->{
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = it.message
                                )
                            }
                        }
                    }

                }
                
                Scaffold(scaffoldState = scaffoldState) {
                    Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = viewModel.searchQuery.collectAsState().value,
                                onValueChange = viewModel::onSearch,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(text = "Search Your Word...")
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ){
                                items(state.value.wordInfoItems.size){
                                    val wordInfoItem = state.value.wordInfoItems[it]
                                    if (it > 0){
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    WordInfoItem(wordInfo = wordInfoItem)

                                    if (it< state.value.wordInfoItems.size -1){
                                        Divider()
                                    }
                                }
                            }

                        }
                    }
                    
                }

            }
        }
    }
}
