package com.w1tty.movies.domain.model

import java.util.*

data class MovieDetail(
  val id: String,
  val overview: String,
  val releaseDate: Date
)