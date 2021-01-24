package com.w1tty.movies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.w1tty.movies.data.database.entity.MovieEntity

@Dao
interface MoviesDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun save(items: List<MovieEntity>)
  
  @Query("DELETE FROM movies")
  suspend fun clear()
  
  @Query("SELECT * FROM movies")
  suspend fun getMovies(): List<MovieEntity>
  
}