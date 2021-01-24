package com.w1tty.movies.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
  @PrimaryKey val id: String,
  val overview: String,
  val releaseDate: Long
)