package com.example.mymovies.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymovies.ui.viewmodel.MovieViewModel

@Composable
fun SavedMoviesScreen(
    viewModel: MovieViewModel
) {
    val savedMovies by viewModel.databaseMovies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Saved Movies",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(savedMovies) { movie ->
                MovieCard(movie = movie)
            }
        }
    }
} 