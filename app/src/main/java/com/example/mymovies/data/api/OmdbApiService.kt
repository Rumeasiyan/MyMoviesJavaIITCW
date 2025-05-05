package com.example.mymovies.data.api

import com.example.mymovies.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchMovieByTitle(
        @Query("t") title: String,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): Response<MovieResponse>

    @GET("/")
    suspend fun searchMovies(
        @Query("s") searchQuery: String,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): Response<SearchResponse>
}

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