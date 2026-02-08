package com.example.consumapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.consumapi.data.local.DatabaseProvider
import com.example.consumapi.ui.screen.CapturedScreen
import com.example.consumapi.ui.screen.WantedSearchScreen
import com.example.consumapi.data.repository.FBIRepository
import com.example.consumapi.ui.viewmodel.WantedViewModelFactory

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
    val context = LocalContext.current
    // remember per evitar crear la BD i el repo a cada compilacio
    val dao = remember { DatabaseProvider.getDb(context).wantedDao() }
    val repo = remember { FBIRepository(dao) }

    val wantedViewModel: WantedViewModel =
        viewModel(factory = WantedViewModelFactory(repo))
    val navController = rememberNavController()

    // per saber a quina pantalla estem (i marcar la icona seleccionada)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Amaguem la bottom bar al detail
    val showBottomBar = currentRoute?.startsWith("detail/") != true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "search",
                        onClick = { navController.navigate("search") },
                        icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                        label = { Text("Search") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "captured",
                        onClick = { navController.navigate("captured") },
                        icon = { Icon(Icons.Filled.Star, contentDescription = "Capturats") },
                        label = { Text("Capturats") }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                // Home = la teva llista sense SearchBar
                WantedListScreen(
                    viewModel = wantedViewModel,
                    onPersonClick = { uid -> navController.navigate("detail/$uid") }
                )
            }

            composable("search") {
                // Search = pantalla amb buscador + llista filtrada
                WantedSearchScreen(
                    viewModel = wantedViewModel,
                    onPersonClick = { uid -> navController.navigate("detail/$uid") }
                )
            }

            composable("captured") {
                CapturedScreen(viewModel = wantedViewModel)
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
}