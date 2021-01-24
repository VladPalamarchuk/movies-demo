package com.w1tty.movies.domain.interactor

import com.w1tty.movies.domain.base.BaseUseCase
import com.w1tty.movies.domain.base.Result
import com.w1tty.movies.domain.model.MovieDetail
import com.w1tty.movies.domain.repository.local.MoviesLocalRepository
import com.w1tty.movies.domain.repository.remote.MoviesRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
  private val remoteRepository: MoviesRemoteRepository,
  private val localRepository: MoviesLocalRepository
) : BaseUseCase<GetMovieDetailsUseCase.Params, Flow<Result<MovieDetail>>>() {
  
  override suspend fun execute(params: Params): Flow<Result<MovieDetail>> {
    return flow {
      if (params.useCache) {
        val cacheMovie = localRepository.getMovieDetail(params.movieId)
        if (cacheMovie != null) {
          emit(Result.success(cacheMovie))
        }
      }
      val remoteMovie = remoteRepository.getMovieDetail(params.movieId)
      emit(remoteMovie)
      remoteMovie.data?.let { localRepository.save(it) }
    }
  }
  
  data class Params(
    val movieId: String,
    val useCache: Boolean
  )
}