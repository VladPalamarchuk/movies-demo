package com.w1tty.movies.demo.ui.screens.details

import androidx.lifecycle.ViewModel
import com.w1tty.movies.demo.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsFragmentModule {

  @ContributesAndroidInjector
  internal abstract fun bindFragment(): MovieDetailsFragment

  @Binds
  @IntoMap
  @ViewModelKey(MovieDetailsViewModel::class)
  internal abstract fun bindViewModel(viewModel: MovieDetailsViewModel): ViewModel

}