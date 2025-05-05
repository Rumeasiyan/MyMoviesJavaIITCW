package com.example.mymovies.navigation

sealed class Screens(val route: String) {
    object Search : Screens("search")
    object Saved : Screens("saved")
    object Actors : Screens("actors")
    object AllMovies : Screens("all_movies")
} 