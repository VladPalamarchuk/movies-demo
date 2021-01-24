package com.w1tty.movies.data.api.base

import com.w1tty.movies.domain.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.concurrent.CancellationException

suspend fun <T, R> getResult(call: suspend () -> Response<T>, mapper: (T) -> R): Result<R> {
  try {
    val response = call.invoke()
    if (response.isSuccessful) {
      val body = response.body()
      if (body != null) {
        return withContext(Dispatchers.Default) {
          Result.success(mapper(body))
        }
      }
    }
    return Result.error(NetworkError(response.code()))
  } catch (e: Exception) {
    return if (e is CancellationException) {
      Result.cancelled()
    } else {
      Result.error(NetworkError())
    }
  }
}