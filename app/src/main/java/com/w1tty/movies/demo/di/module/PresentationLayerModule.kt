package com.w1tty.movies.demo.di.module

import com.w1tty.movies.demo.di.screens.ScreensModule
import com.w1tty.movies.demo.di.viewmodel.ViewModelModule
import dagger.Module

@Module(includes = [
  ViewModelModule::class,
  ScreensModule::class
])
class PresentationLayerModule