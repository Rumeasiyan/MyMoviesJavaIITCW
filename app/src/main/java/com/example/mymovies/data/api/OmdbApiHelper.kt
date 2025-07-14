package com.example.mymovies.data.api

import com.example.mymovies.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class OmdbApiHelper(private val apiKey: String = BuildConfig.OMDB_API_KEY) {
    private val baseUrl = "https://www.omdbapi.com/"

    suspend fun searchMovieByTitle(title: String): Result<MovieResponse> = withContext(Dispatchers.IO) {
        try {
            val query = "?t=" + title.replace(" ", "+") + "&apikey=$apiKey"
            val connection = (URL(baseUrl + query).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
            }
            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
            val response = stream.bufferedReader().use(BufferedReader::readText)
            val json = JSONObject(response)
            if (json.optString("Response") == "True") {
                Result.success(jsonToMovieResponse(json))
            } else {
                Result.failure(Exception(json.optString("Error", "Unknown error")))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchMovies(query: String): Result<SearchResponse> = withContext(Dispatchers.IO) {
        try {
            val queryString = "?s=" + query.replace(" ", "+") + "&apikey=$apiKey"
            val connection = (URL(baseUrl + queryString).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
            }
            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
            val response = stream.bufferedReader().use(BufferedReader::readText)
            val json = JSONObject(response)
            if (json.optString("Response") == "True") {
                Result.success(jsonToSearchResponse(json))
            } else {
                Result.failure(Exception(json.optString("Error", "Unknown error")))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun jsonToMovieResponse(json: JSONObject): MovieResponse {
        return MovieResponse(
            Title = json.optString("Title"),
            Year = json.optString("Year"),
            Rated = json.optString("Rated"),
            Released = json.optString("Released"),
            Runtime = json.optString("Runtime"),
            Genre = json.optString("Genre"),
            Director = json.optString("Director"),
            Writer = json.optString("Writer"),
            Actors = json.optString("Actors"),
            Plot = json.optString("Plot"),
            Response = json.optString("Response"),
            Error = json.optString("Error", null)
        )
    }

    private fun jsonToSearchResponse(json: JSONObject): SearchResponse {
        val searchList = mutableListOf<SearchMovie>()
        val searchArray = json.optJSONArray("Search")
        if (searchArray != null) {
            for (i in 0 until searchArray.length()) {
                val item = searchArray.getJSONObject(i)
                searchList.add(
                    SearchMovie(
                        Title = item.optString("Title"),
                        Year = item.optString("Year"),
                        imdbID = item.optString("imdbID"),
                        Type = item.optString("Type"),
                        Poster = item.optString("Poster")
                    )
                )
            }
        }
        return SearchResponse(
            Search = if (searchList.isNotEmpty()) searchList else null,
            totalResults = json.optString("totalResults", null),
            Response = json.optString("Response"),
            Error = json.optString("Error", null)
        )
    }
} 