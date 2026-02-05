package com.example.consumapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.consumapi.ui.viewmodel.WantedViewModel

@Composable
fun WantedListScreen(viewModel: WantedViewModel) {

    val wantedList by viewModel.wantedList.observeAsState(emptyList())
    val uiState by viewModel.uiState.observeAsState()

    when (uiState) {
        is WantedViewModel.UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is WantedViewModel.UIState.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(wantedList) { person ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            val imageUrl = person.images?.firstOrNull()?.original
                            if (!imageUrl.isNullOrEmpty()) {
                                Image(
                                    painter = rememberAsyncImagePainter(imageUrl),
                                    contentDescription = person.title ?: "Sin nombre",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = person.title ?: "Sin nombre"
                                )
                                person.rewardText?.let { reward ->
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = reward
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        is WantedViewModel.UIState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error al cargar datos")
            }
        }

        else -> {}
    }
}
