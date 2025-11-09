package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.ServiceViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ServiceFinderApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceFinderApp(viewModel: ServiceViewModel) {
    val navController = rememberNavController()
    val currentLocation by viewModel.currentLocation.collectAsState()
    var showLocationScreen by remember { mutableStateOf(currentLocation == null) }

    LaunchedEffect(currentLocation) {
        if (currentLocation != null) {
            showLocationScreen = false
        }
    }

    if (showLocationScreen) {
        LocationScreen(
            viewModel = viewModel,
            onLocationSet = { showLocationScreen = false }
        )
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Chat, contentDescription = "Chat") },
                        label = { Text("AI Chat") },
                        selected = currentDestination?.route == "chat",
                        onClick = {
                            navController.navigate("chat") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Dashboard, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        selected = currentDestination?.route == "dashboard",
                        onClick = {
                            navController.navigate("dashboard") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "chat",
                modifier = Modifier.padding(padding)
            ) {
                composable("chat") {
                    ChatScreen(
                        viewModel = viewModel,
                        onProviderClick = { providerId ->
                            navController.navigate("provider/$providerId")
                        }
                    )
                }

                composable("dashboard") {
                    DashboardScreen(
                        viewModel = viewModel,
                        onProviderClick = { providerId ->
                            navController.navigate("provider/$providerId")
                        }
                    )
                }

                composable(
                    route = "provider/{providerId}",
                    arguments = listOf(navArgument("providerId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val providerId =
                        backStackEntry.arguments?.getString("providerId") ?: return@composable
                    ProviderDetailsScreen(
                        viewModel = viewModel,
                        providerId = providerId,
                        onBack = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}