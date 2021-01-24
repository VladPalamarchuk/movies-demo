package com.w1tty.movies.data.database.mapper

import com.w1tty.movies.data.database.entity.MovieEntity
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.utility.mapper.Mapper
import javax.inject.Inject

class MoviesEntityMapper @Inject constructor() : Mapper<Movie, MovieEntity>() {
  override fun map(from: Movie): MovieEntity {
    return with(from) {
      MovieEntity(
        id,
        title,
        picture
      )
    }
  }
  
  override fun reverse(to: MovieEntity): Movie {
    return with(to) {
      Movie(
        id,
        title,
        picture
      )
    }
  }
}