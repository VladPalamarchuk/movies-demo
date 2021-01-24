package com.w1tty.movies.data.mapper

import com.w1tty.movies.data.BuildConfig
import com.w1tty.movies.data.api.model.MovieAm
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.utility.mapper.BaseMapper
import javax.inject.Inject

class MoviesAmMapper @Inject constructor() : BaseMapper<MovieAm, Movie>() {
  
  override fun map(from: MovieAm): Movie {
    return with(from) {
      Movie(
        id,
        title,
        getPicturePath(picture)
      )
    }
  }
  
  private fun getPicturePath(path: String?): String? {
    return path?.let {
      BuildConfig.PICTURE_BASE_URL + path
    }
  }
}