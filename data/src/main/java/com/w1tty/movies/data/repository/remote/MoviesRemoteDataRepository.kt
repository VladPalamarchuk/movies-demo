package com.w1tty.movies.data.repository.remote

import com.w1tty.movies.data.api.MoviesApi
import com.w1tty.movies.data.api.base.getResult
import com.w1tty.movies.data.mapper.MovieDetailAmMapper
import com.w1tty.movies.data.mapper.MoviesAmMapper
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.domain.model.MovieDetail
import com.w1tty.movies.domain.repository.remote.MoviesRemoteRepository
import javax.inject.Inject

class MoviesRemoteDataRepository @Inject constructor(
  private val api: MoviesApi,
  private val mapper: MoviesAmMapper,
  private val detailsMapper: MovieDetailAmMapper
) : MoviesRemoteRepository {
  
  override suspend fun getMovies(page: Int, query: String): Result<List<Movie>> {
    return getResult({
      if (query.isBlank()) {
        api.getPopularMovies(page)
      } else {
        api.searchMovies(page, query)
      }
    }, {
      mapper.mapList(it.results)
    })
  }
  
  override suspend fun getMovieDetail(id: String): Result<MovieDetail> {
    return getResult({
      api.getMovieDetail(id)
    }, {
      detailsMapper.map(it)
    })
  }
}