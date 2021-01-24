package com.w1tty.movies.data.api.provider

import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.squareup.moshi.Moshi
import com.w1tty.movies.data.BuildConfig
import com.w1tty.movies.data.api.provider.interceptor.AccessTokenInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitMoshiServiceProvider @Inject constructor(
  moshi: Moshi
) {

  private var retrofit: Retrofit
  private var client: OkHttpClient

  init {
    // Create Http client
    val builder = OkHttpClient.Builder().apply {
      if (BuildConfig.DEBUG) {
        addInterceptor(OkHttpProfilerInterceptor())
      }
      connectTimeout(90, TimeUnit.SECONDS)
      callTimeout(90, TimeUnit.SECONDS)
      readTimeout(90, TimeUnit.SECONDS)
      addInterceptor(AccessTokenInterceptor())
    }

    client = builder.build()

    retrofit = Retrofit.Builder()
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .baseUrl(BuildConfig.API_BASE_URL)
      .build()
  }

  fun <T> getService(clazz: Class<T>): T {
    return retrofit.create(clazz)
  }

  fun provideHttpClient() = client

}
