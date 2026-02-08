package com.example.consumapi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
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
fun WantedSearchScreen(
    viewModel: WantedViewModel,
    onPersonClick: (String) -> Unit
) {
    val query by viewModel.searchQuery.observeAsState("")
    val wantedList by viewModel.filteredWantedList.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Cerca per nom") },
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        if (wantedList.isEmpty() && query.isNotBlank()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No s'han trobat resultats")
            }
            return
        }

        LazyColumn {
            items(wantedList) { person ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            val uid = person.uid
                            if (!uid.isNullOrBlank()) onPersonClick(uid)
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val imageUrl = person.images?.firstOrNull()?.thumb
                            ?: person.images?.firstOrNull()?.original

                        AsyncImage(
                            model = imageUrl,
                            contentDescription = person.title ?: "Foto",
                            placeholder = painterResource(R.drawable.imatge_perfil_predeterminat),
                            error = painterResource(R.drawable.imatge_perfil_predeterminat),
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(12.dp))

                        Text(
                            text = person.title ?: "Sin nombre",
                            modifier = Modifier.weight(1f),
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}
