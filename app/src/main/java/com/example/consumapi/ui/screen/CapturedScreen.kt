package com.example.consumapi.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.consumapi.R
import com.example.consumapi.ui.viewmodel.WantedViewModel

@Composable
fun CapturedScreen(viewModel: WantedViewModel) {

    // Llegeix la llista de capturats que ve de Room
    val capturedList by viewModel.captured.observeAsState(emptyList())

    // Si no hi ha capturats, mostrem un missatge
    if (capturedList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Encara no tens ningú capturat ⭐")
        }
        return
    }

    // Mostra els capturats en forma de llista
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(capturedList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {

                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.title,
                        placeholder = painterResource(R.drawable.imatge_perfil_predeterminat),
                        error = painterResource(R.drawable.imatge_perfil_predeterminat),
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f),
                        maxLines = 2
                    )
                }
            }
        }
    }
}