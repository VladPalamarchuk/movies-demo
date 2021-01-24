package com.w1tty.movies.domain.repository.local

import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.domain.model.MovieDetail

interface MoviesLocalRepository {
  
  suspend fun save(items: List<Movie>)
  
  suspend fun getMovies(): List<Movie>
  
  suspend fun save(item: MovieDetail)
  
  suspend fun getMovieDetail(id: String): MovieDetail?
  
}