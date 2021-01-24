package com.w1tty.movies.demo.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.w1tty.movies.demo.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
