package com.w1tty.movies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.w1tty.movies.data.database.entity.MovieDetailEntity

@Dao
interface MovieDetailsDao {
  
  @Query("SELECT * FROM movie_detail WHERE id = :id")
  suspend fun getMovieDetail(id: String): MovieDetailEntity?
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveMovieDetail(item: MovieDetailEntity)
  
}