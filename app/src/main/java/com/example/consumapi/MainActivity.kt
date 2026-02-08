package com.example.consumapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.consumapi.ui.screen.WantedDetailScreen
import com.example.consumapi.ui.screen.WantedListScreen
import com.example.consumapi.ui.theme.ConsumAPITheme
import com.example.consumapi.ui.viewmodel.WantedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConsumAPITheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val wantedViewModel: WantedViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            WantedListScreen(
                viewModel = wantedViewModel,
                onPersonClick = { uid -> navController.navigate("detail/$uid") }
            )
        }

        composable(
            route = "detail/{uid}",
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: return@composable
            WantedDetailScreen(
                viewModel = wantedViewModel,
                uid = uid,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
