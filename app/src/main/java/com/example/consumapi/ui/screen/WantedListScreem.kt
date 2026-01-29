package com.example.consumapi.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.consumapi.ui.viewmodel.WantedViewModel

@Composable
fun WantedListScreen(viewModel: WantedViewModel) {

    val wantedList by viewModel.wantedList.observeAsState(emptyList())
    val uiState by viewModel.uiState.observeAsState()

    when (uiState) {
        is WantedViewModel.UIState.Loading -> {
            CircularProgressIndicator()
        }

        is WantedViewModel.UIState.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(wantedList) { person ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = person.title ?: "Sin nombre",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        is WantedViewModel.UIState.Error -> {
            Text(text = "Error al cargar datos")
        }

        else -> {}
    }
}
