package com.w1tty.movies.domain.base

abstract class BaseUseCase<P, T> {
  abstract suspend fun execute(params: P): T
}