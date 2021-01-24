package com.w1tty.movies.data.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.w1tty.movies.data.api.MoviesApi
import com.w1tty.movies.data.api.provider.RetrofitMoshiServiceProvider
import com.w1tty.movies.utility.annotations.AppContext
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import javax.inject.Singleton

@Module
internal class ApiModule {

  @Provides
  @Singleton
  internal fun provideDefaultMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  internal fun provideMoshiRetrofitServiceProvider(
    moshi: Moshi,
  ) = RetrofitMoshiServiceProvider(
    moshi
  )

  @Provides
  @Singleton
  internal fun provideMoviesApi(provider: RetrofitMoshiServiceProvider): MoviesApi {
    return provider.getService(MoviesApi::class.java)
  }
}