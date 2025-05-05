package com.example.mymovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            navController = navController,
                            onAddMoviesClick = { viewModel.addPredefinedMoviesToDb() }
                        )
                    }
                    composable("search_movies") {
                        SearchMoviesScreen(viewModel = viewModel)
                    }
                    composable("search_actors") {
                        SearchActorsScreen(viewModel = viewModel)
                    }
                    composable("search_all_movies") {
                        SearchAllMoviesScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}