package com.example.consumapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consumapi.ui.screen.WantedListScreen
import com.example.consumapi.ui.theme.ConsumAPITheme
import com.example.consumapi.ui.viewmodel.WantedViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ConsumAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val wantedViewModel: WantedViewModel = viewModel()
    WantedListScreen(viewModel = wantedViewModel)
}
