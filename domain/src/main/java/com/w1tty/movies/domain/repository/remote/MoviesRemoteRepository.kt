package com.w1tty.movies.domain.repository.remote

import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.domain.model.MovieDetail

interface MoviesRemoteRepository {
  
  suspend fun getMovies(page: Int = 1, query: String): Result<List<Movie>>
  
  suspend fun getMovieDetail(id: String): Result<MovieDetail>

}