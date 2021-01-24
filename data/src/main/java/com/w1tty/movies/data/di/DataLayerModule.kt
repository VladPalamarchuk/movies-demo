package com.w1tty.movies.data.di

import dagger.Module

@Module(
  includes = [
    ApiModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
  ]
)
class DataLayerModule