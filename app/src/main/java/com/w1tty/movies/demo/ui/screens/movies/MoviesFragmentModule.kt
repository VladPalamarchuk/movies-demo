package com.w1tty.movies.demo.ui.screens.movies

import androidx.lifecycle.ViewModel
import com.w1tty.movies.demo.di.scope.FragmentScope
import com.w1tty.movies.demo.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MoviesFragmentModule {
  
  @FragmentScope
  @ContributesAndroidInjector
  internal abstract fun bindFragment(): MoviesFragment
  
  @Binds
  @IntoMap
  @ViewModelKey(MoviesFragmentViewModel::class)
  internal abstract fun bindViewModel(viewModel: MoviesFragmentViewModel): ViewModel
  
}