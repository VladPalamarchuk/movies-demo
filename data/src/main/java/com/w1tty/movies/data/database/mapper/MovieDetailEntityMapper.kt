package com.w1tty.movies.data.database.mapper

import com.w1tty.movies.data.database.entity.MovieDetailEntity
import com.w1tty.movies.domain.model.MovieDetail
import com.w1tty.movies.utility.mapper.Mapper
import java.util.*
import javax.inject.Inject

class MovieDetailEntityMapper @Inject constructor() : Mapper<MovieDetail, MovieDetailEntity>() {
  override fun map(from: MovieDetail): MovieDetailEntity {
    return with(from) {
      MovieDetailEntity(
        id,
        overview,
        releaseDate.time
      )
    }
  }
  
  override fun reverse(to: MovieDetailEntity): MovieDetail {
    return with(to) {
      MovieDetail(
        id,
        overview,
        Date(releaseDate)
      )
    }
  }
}