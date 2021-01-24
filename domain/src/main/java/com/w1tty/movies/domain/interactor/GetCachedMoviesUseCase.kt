package com.w1tty.movies.domain.interactor

import com.w1tty.movies.domain.base.BaseUseCase
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.base.WithoutParams
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.domain.repository.local.MoviesLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCachedMoviesUseCase @Inject constructor(
  private val localRepository: MoviesLocalRepository
) : BaseUseCase<WithoutParams, Result<List<Movie>>>() {
  
  override suspend fun execute(params: WithoutParams): Result<List<Movie>> {
    return withContext(Dispatchers.IO) {
      Result.success(localRepository.getMovies())
    }
  }
}