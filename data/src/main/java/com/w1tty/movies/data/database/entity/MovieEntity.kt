package com.w1tty.movies.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
  @PrimaryKey val id: String,
  val title: String,
  val picture: String?
)