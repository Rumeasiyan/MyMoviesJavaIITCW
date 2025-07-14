package com.example.mymovies.data.api

// Only data classes for OMDb API responses are defined here.

data class MovieResponse(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Response: String,
    val Error: String?
)

data class SearchResponse(
    val Search: List<SearchMovie>?,
    val totalResults: String?,
    val Response: String,
    val Error: String?
)

data class SearchMovie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
) 