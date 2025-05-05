package com.example.mymovies.di

import android.content.Context
import androidx.room.Room
import com.example.mymovies.data.db.MovieDatabase
import com.example.mymovies.data.db.MovieDao

object DatabaseModule {
    private var database: MovieDatabase? = null

    fun getDatabase(context: Context): MovieDatabase {
        return database ?: synchronized(this) {
            database ?: buildDatabase(context).also { database = it }
        }
    }

    private fun buildDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movies.db"
        ).build()
    }

    fun getMovieDao(context: Context): MovieDao {
        return getDatabase(context).movieDao()
    }
} 