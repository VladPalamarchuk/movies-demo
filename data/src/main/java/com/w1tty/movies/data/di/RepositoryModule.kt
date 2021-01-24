package com.w1tty.movies.data.di

import com.w1tty.movies.data.repository.local.MoviesLocalDataRepository
import com.w1tty.movies.data.repository.remote.MoviesRemoteDataRepository
import com.w1tty.movies.domain.repository.local.MoviesLocalRepository
import com.w1tty.movies.domain.repository.remote.MoviesRemoteRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {
  
  @Binds
  @Singleton
  internal abstract fun bindMoviesRemoteRepository(
    p: MoviesRemoteDataRepository
  ): MoviesRemoteRepository
  
  @Binds
  @Singleton
  internal abstract fun bindMoviesLocalRepository(
    p: MoviesLocalDataRepository
  ): MoviesLocalRepository
  
}