package com.w1tty.movies.demo

import com.w1tty.movies.demo.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MovieApplication : DaggerApplication() {
  
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.factory().create(this)
  }
  
  override fun onCreate() {
    super.onCreate()
    
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
      Timber.tag("W1TTY")
    }
  }
}