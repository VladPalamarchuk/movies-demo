package com.w1tty.movies.data.repository.local

import com.w1tty.movies.data.database.dao.MovieDetailsDao
import com.w1tty.movies.data.database.dao.MoviesDao
import com.w1tty.movies.data.database.mapper.MovieDetailEntityMapper
import com.w1tty.movies.data.database.mapper.MoviesEntityMapper
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.domain.model.MovieDetail
import com.w1tty.movies.domain.repository.local.MoviesLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalDataRepository @Inject constructor(
  private val dao: MoviesDao,
  private val movieDetailsDao: MovieDetailsDao,
  private val mapper: MoviesEntityMapper,
  private val movieDetailEntityMapper: MovieDetailEntityMapper
) : MoviesLocalRepository {
  
  override suspend fun save(items: List<Movie>) {
    val mapper = withContext(Dispatchers.Default) {
      mapper.mapList(items)
    }
    dao.clear()
    dao.save(mapper)
  }
  
  override suspend fun getMovies(): List<Movie> {
    val movies = dao.getMovies()
    return withContext(Dispatchers.Default) {
      mapper.reverseList(movies)
    }
  }
  
  override suspend fun save(item: MovieDetail) {
    val mapped = withContext(Dispatchers.Default) {
      movieDetailEntityMapper.map(item)
    }
    movieDetailsDao.saveMovieDetail(mapped)
  }
  
  override suspend fun getMovieDetail(id: String): MovieDetail? {
    val movie = movieDetailsDao.getMovieDetail(id)
    if (movie != null) {
      return withContext(Dispatchers.Default) {
        movieDetailEntityMapper.reverse(movie)
      }
    }
    return null
  }
}