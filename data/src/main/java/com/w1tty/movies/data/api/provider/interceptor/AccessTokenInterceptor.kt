package com.w1tty.movies.data.api.provider.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor() : Interceptor {

  companion object {
    private const val API_KEY = "1cc33b07c9aa5466f88834f7042c9258"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val httpUrl = request.url

    val authenticatedUrl = httpUrl.newBuilder()
      .addQueryParameter("api_key", API_KEY)
      .build()

    return chain.proceed(request.newBuilder().url(authenticatedUrl).build())
  }
}