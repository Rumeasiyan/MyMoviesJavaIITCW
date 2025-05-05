package com.example.mymovies.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymovies.data.api.MovieResponse
import com.example.mymovies.ui.viewmodel.MovieViewModel

@Composable
fun SearchMoviesScreen(
    viewModel: MovieViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResult by viewModel.searchResult.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter movie title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.searchMovieByTitle(searchQuery) }
            ) {
                Text("Retrieve Movie")
            }

            Button(
                onClick = { viewModel.saveCurrentMovie() }
            ) {
                Text("Save to Database")
            }
        }

        searchResult?.let { movie ->
            MovieDetails(movie = movie)
        }

        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun MovieDetails(movie: MovieResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Title: ${movie.Title}")
        Text("Year: ${movie.Year}")
        Text("Rated: ${movie.Rated}")
        Text("Released: ${movie.Released}")
        Text("Runtime: ${movie.Runtime}")
        Text("Genre: ${movie.Genre}")
        Text("Director: ${movie.Director}")
        Text("Writer: ${movie.Writer}")
        Text("Actors: ${movie.Actors}")
        Text("Plot: ${movie.Plot}")
    }
} 