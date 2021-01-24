package com.w1tty.movies.demo.di.screens

import com.w1tty.movies.demo.ui.screens.details.MovieDetailsFragmentModule
import com.w1tty.movies.demo.ui.screens.movies.MoviesFragmentModule
import dagger.Module

@Module(includes = [
  MoviesFragmentModule::class,
  MovieDetailsFragmentModule::class,
])
class ScreensModule