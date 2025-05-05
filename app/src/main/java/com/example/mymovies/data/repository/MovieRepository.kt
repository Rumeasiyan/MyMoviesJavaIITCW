package com.example.mymovies.data.repository

import com.example.mymovies.data.api.MovieResponse
import com.example.mymovies.data.api.OmdbApiService
import com.example.mymovies.data.api.SearchResponse
import com.example.mymovies.data.db.MovieDao
import com.example.mymovies.data.db.MovieEntity

class MovieRepository(
    private val api: OmdbApiService,
    private val dao: MovieDao
) {
    suspend fun searchMovieByTitle(title: String): Result<MovieResponse> {
        return try {
            val response = api.searchMovieByTitle(title)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchMovies(query: String): Result<SearchResponse> {
        return try {
            val response = api.searchMovies(query)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveMovie(movieResponse: MovieResponse) {
        val movieEntity = MovieEntity(
            title = movieResponse.Title,
            year = movieResponse.Year,
            rated = movieResponse.Rated,
            released = movieResponse.Released,
            runtime = movieResponse.Runtime,
            genre = movieResponse.Genre,
            director = movieResponse.Director,
            writer = movieResponse.Writer,
            actors = movieResponse.Actors,
            plot = movieResponse.Plot
        )
        dao.insertMovie(movieEntity)
    }

    suspend fun searchMoviesByActor(actorName: String): List<MovieEntity> {
        return dao.searchMoviesByActor(actorName)
    }

    suspend fun getAllMovies(): List<MovieEntity> {
        return dao.getAllMovies()
    }
} 