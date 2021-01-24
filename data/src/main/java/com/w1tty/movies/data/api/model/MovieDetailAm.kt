package com.w1tty.movies.data.api.model

import com.squareup.moshi.Json

data class MovieDetailAm(
  @Json(name = "id") val id: String,
  @Json(name = "overview") val overview: String,
  @Json(name = "release_date") val releaseDate: String,
)