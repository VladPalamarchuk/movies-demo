package com.w1tty.movies.domain.base

data class Result<out T>(val status: Status, val data: T?, val throwable: Throwable?) {
  
  enum class Status {
    SUCCESS,
    ERROR,
    CANCELLED
  }
  
  companion object {
    fun <T> success(data: T): Result<T> {
      return Result(Status.SUCCESS, data, null)
    }
    
    fun <T> error(throwable: Throwable?, data: T? = null): Result<T> {
      return Result(Status.ERROR, data, throwable)
    }
    
    fun <T> cancelled(): Result<T> {
      return Result(Status.CANCELLED, null, null)
    }
  }
}