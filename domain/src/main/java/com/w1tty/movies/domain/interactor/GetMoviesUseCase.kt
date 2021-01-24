package com.w1tty.movies.domain.interactor

import com.w1tty.movies.domain.base.BaseUseCase
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.model.Movie
import com.w1tty.movies.domain.repository.local.MoviesLocalRepository
import com.w1tty.movies.domain.repository.remote.MoviesRemoteRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
  private val remoteRepository: MoviesRemoteRepository,
  private val localRepository: MoviesLocalRepository
) : BaseUseCase<GetMoviesUseCase.Params, Result<List<Movie>>>() {
  
  override suspend fun execute(params: Params): Result<List<Movie>> {
    val remoteMovies = remoteRepository.getMovies(params.page, params.query)
    // save first page result to storage
    val updateCache = params.page == 1 && params.query.isBlank() && remoteMovies.status == Result.Status.SUCCESS
    if (updateCache) {
      remoteMovies.data?.let { localRepository.save(it) }
    }
    return remoteMovies
  }
  
  data class Params(
    val page: Int,
    val query: String
  )
}