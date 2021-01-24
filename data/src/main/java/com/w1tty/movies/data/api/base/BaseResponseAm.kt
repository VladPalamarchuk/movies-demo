package com.w1tty.movies.data.api.base

data class BaseResponseAm<T>(
  val page: Int,
  val results: T
)