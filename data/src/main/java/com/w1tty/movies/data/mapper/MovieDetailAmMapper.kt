package com.w1tty.movies.data.mapper

import com.w1tty.movies.data.api.model.MovieDetailAm
import com.w1tty.movies.data.utils.parseDate
import com.w1tty.movies.domain.model.MovieDetail
import com.w1tty.movies.utility.mapper.BaseMapper
import javax.inject.Inject

class MovieDetailAmMapper @Inject constructor() : BaseMapper<MovieDetailAm, MovieDetail>() {
  
  override fun map(from: MovieDetailAm): MovieDetail {
    return with(from) {
      MovieDetail(
        id,
        overview,
        parseDate(releaseDate)
      )
    }
  }
}