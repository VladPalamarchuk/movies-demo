package com.w1tty.movies.data.api.model

import com.squareup.moshi.Json

data class MovieAm(
  @Json(name = "id") val id: String,
  @Json(name = "title") val title: String,
  @Json(name = "poster_path") val picture: String?
)