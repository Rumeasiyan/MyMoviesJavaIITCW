package com.example.mymovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mymovies.navigation.Screens
import com.example.mymovies.ui.screens.*
import com.example.mymovies.ui.viewmodel.MovieViewModel
import com.example.mymovies.ui.theme.MyMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMoviesTheme {
                val navController = rememberNavController()
                val viewModel = remember { MovieViewModel(this) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                                label = { Text("Search") },
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.Search.route } == true,
                                onClick = { navController.navigate(Screens.Search.route) {
                                    popUpTo(Screens.Search.route) { inclusive = true }
                                }}
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Saved") },
                                label = { Text("Saved") },
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.Saved.route } == true,
                                onClick = { navController.navigate(Screens.Saved.route) {
                                    popUpTo(Screens.Saved.route) { inclusive = true }
                                }}
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Person, contentDescription = "Actors") },
                                label = { Text("Actors") },
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.Actors.route } == true,
                                onClick = { navController.navigate(Screens.Actors.route) {
                                    popUpTo(Screens.Actors.route) { inclusive = true }
                                }}
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.List, contentDescription = "All Movies") },
                                label = { Text("All Movies") },
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.AllMovies.route } == true,
                                onClick = { navController.navigate(Screens.AllMovies.route) {
                                    popUpTo(Screens.AllMovies.route) { inclusive = true }
                                }}
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Search.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Screens.Search.route) {
                            SearchMoviesScreen(viewModel = viewModel)
                        }
                        composable(Screens.Saved.route) {
                            SavedMoviesScreen(viewModel = viewModel)
                        }
                        composable(Screens.Actors.route) {
                            SearchActorsScreen(viewModel = viewModel)
                        }
                        composable(Screens.AllMovies.route) {
                            SearchAllMoviesScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}