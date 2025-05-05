package com.example.mymovies.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.data.api.MovieResponse
import com.example.mymovies.data.db.MovieEntity
import com.example.mymovies.data.repository.MovieRepository
import com.example.mymovies.di.DatabaseModule
import com.example.mymovies.di.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(context: Context) : ViewModel() {
    private val repository = MovieRepository(
        api = NetworkModule.omdbApiService,
        dao = DatabaseModule.getMovieDao(context)
    )

    private val _searchResult = MutableStateFlow<MovieResponse?>(null)
    val searchResult: StateFlow<MovieResponse?> = _searchResult

    private val _searchResults = MutableStateFlow<List<MovieResponse>>(emptyList())
    val searchResults: StateFlow<List<MovieResponse>> = _searchResults

    private val _databaseMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val databaseMovies: StateFlow<List<MovieEntity>> = _databaseMovies

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    fun searchMovieByTitle(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.searchMovieByTitle(title)
                    .onSuccess { _searchResult.value = it }
                    .onFailure { _error.value = it.message }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _isSearching.value = true
            try {
                repository.searchMovies(query)
                    .onSuccess { response ->
                        response.Search?.map { searchMovie ->
                            repository.searchMovieByTitle(searchMovie.Title)
                                .getOrNull()
                        }?.filterNotNull()?.let {
                            _searchResults.value = it
                        }
                    }
                    .onFailure { _error.value = it.message }
            } finally {
                _isSearching.value = false
            }
        }
    }

    fun saveCurrentMovie() {
        viewModelScope.launch {
            _searchResult.value?.let { repository.saveMovie(it) }
        }
    }

    fun searchMoviesByActor(actorName: String) {
        viewModelScope.launch {
            _databaseMovies.value = repository.searchMoviesByActor(actorName)
        }
    }

    fun loadAllMovies() {
        viewModelScope.launch {
            _databaseMovies.value = repository.getAllMovies()
        }
    }

    // Function to add predefined movies to database
    fun addPredefinedMoviesToDb() {
        viewModelScope.launch {
            predefinedMovies.forEach { movie ->
                repository.searchMovieByTitle(movie)
                    .onSuccess { repository.saveMovie(it) }
            }
        }
    }

    private val predefinedMovies = listOf(
        "The Shawshank Redemption",
        "The Godfather",
        "The Dark Knight",
        "12 Angry Men",
        "Schindler's List"
    )
} 