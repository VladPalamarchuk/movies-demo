package com.w1tty.movies.demo.di.component

import com.w1tty.movies.data.di.DataLayerModule
import com.w1tty.movies.demo.MovieApplication
import com.w1tty.movies.demo.di.module.AppContextModule
import com.w1tty.movies.demo.di.module.PresentationLayerModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidSupportInjectionModule::class,
  AppContextModule::class,
  PresentationLayerModule::class,
  DataLayerModule::class
])
interface AppComponent : AndroidInjector<MovieApplication> {

  @Component.Factory
  abstract class AppComponentFactory : AndroidInjector.Factory<MovieApplication>

}