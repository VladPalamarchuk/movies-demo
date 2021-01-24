package com.w1tty.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.w1tty.movies.data.database.dao.MovieDetailsDao
import com.w1tty.movies.data.database.dao.MoviesDao
import com.w1tty.movies.data.database.entity.MovieDetailEntity
import com.w1tty.movies.data.database.entity.MovieEntity

@Database(entities = [
  MovieEntity::class,
  MovieDetailEntity::class
], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
  
  abstract fun getMoviesDao(): MoviesDao
  
  abstract fun getMovieDetailsDao(): MovieDetailsDao
  
}