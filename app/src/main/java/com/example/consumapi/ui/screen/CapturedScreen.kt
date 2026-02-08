package com.example.consumapi.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.consumapi.ui.viewmodel.WantedViewModel

@Composable
fun CapturedScreen(viewModel: WantedViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Capturats (pendents de Room)")
    }
}