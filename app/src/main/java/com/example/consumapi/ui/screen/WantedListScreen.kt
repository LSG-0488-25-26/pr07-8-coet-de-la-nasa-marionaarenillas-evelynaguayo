package com.example.consumapi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.consumapi.R
import com.example.consumapi.ui.viewmodel.WantedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WantedListScreen(
    viewModel: WantedViewModel,
    onPersonClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState(WantedViewModel.UIState.Loading)
    val filteredList by viewModel.filteredWantedList.observeAsState(emptyList())
    val query by viewModel.searchQuery.observeAsState("")

    when (uiState) {
        is WantedViewModel.UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Carregant llista de cercats...")
                }
            }
        }

        is WantedViewModel.UIState.Error -> {
            val msg = (uiState as WantedViewModel.UIState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Error en carregar les dades",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(msg, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadWantedPersons() }) {
                        Text("Tornar a intentar")
                    }
                }
            }
        }

        is WantedViewModel.UIState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Títol de la pantalla principal
                Text(
                    text = "FBI Most Wanted",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // SearchBar integrada al llistat principal
                OutlinedTextField(
                    value = query,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Busca un cercat per nom...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Cerca"
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Esborrar cerca"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Comptador de resultats
                Text(
                    text = if (query.isNotBlank())
                        "${filteredList.size} resultat(s) per \"$query\""
                    else
                        "${filteredList.size} persones cercades",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Missatge de cap resultat
                if (filteredList.isEmpty() && query.isNotBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Cap resultat per \"$query\"",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    return@Column
                }

                // Llistat de persones cercades
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredList) { person ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    val uid = person.uid
                                    if (!uid.isNullOrBlank()) onPersonClick(uid)
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                val imageUrl = person.images?.firstOrNull()?.thumb
                                    ?: person.images?.firstOrNull()?.original

                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = person.title ?: "Foto",
                                    placeholder = painterResource(R.drawable.imatge_perfil_predeterminat),
                                    error = painterResource(R.drawable.imatge_perfil_predeterminat),
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = person.title ?: "Desconegut",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 2
                                    )
                                    person.rewardText?.takeIf { it.isNotBlank() }?.let { reward ->
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = reward,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            maxLines = 1
                                        )
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
