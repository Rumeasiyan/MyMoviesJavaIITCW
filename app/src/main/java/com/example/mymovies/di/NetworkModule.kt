package com.example.mymovies.di

import com.example.mymovies.data.api.OmdbApiHelper

object NetworkModule {
    val omdbApiHelper: OmdbApiHelper = OmdbApiHelper(com.example.mymovies.BuildConfig.OMDB_API_KEY)
} 