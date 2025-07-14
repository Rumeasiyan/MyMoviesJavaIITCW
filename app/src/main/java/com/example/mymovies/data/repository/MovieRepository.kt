package com.example.mymovies.data.repository

import com.example.mymovies.data.api.MovieResponse
import com.example.mymovies.data.api.OmdbApiHelper
import com.example.mymovies.data.api.SearchResponse
import com.example.mymovies.data.db.MovieDao
import com.example.mymovies.data.db.MovieEntity

class MovieRepository(
    private val apiHelper: OmdbApiHelper,
    private val dao: MovieDao
) {
    suspend fun searchMovieByTitle(title: String): Result<MovieResponse> {
        return apiHelper.searchMovieByTitle(title)
    }

    suspend fun searchMovies(query: String): Result<SearchResponse> {
        return apiHelper.searchMovies(query)
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