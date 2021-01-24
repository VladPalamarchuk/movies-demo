package com.w1tty.movies.demo.mapper

import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.utility.mapper.BaseMapper
import javax.inject.Inject

class MoviesPmMapper @Inject constructor() : BaseMapper<Movie, MoviePm>() {
  override fun map(from: Movie): MoviePm {
    return with(from) {
      MoviePm(
        id,
        title,
        picture
      )
    }
  }
}