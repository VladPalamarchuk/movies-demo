package com.w1tty.movies.demo.di.module

import android.content.Context
import com.w1tty.movies.demo.MovieApplication
import com.w1tty.movies.utility.annotations.AppContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppContextModule {

  @Singleton
  @AppContext
  @Binds
  abstract fun provideContext(app: MovieApplication): Context

}