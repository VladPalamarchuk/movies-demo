package com.w1tty.movies.data.di

import android.content.Context
import androidx.room.Room
import com.w1tty.movies.data.database.MoviesDatabase
import com.w1tty.movies.utility.annotations.AppContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
  
  @Singleton
  @Provides
  fun provideDatabase(@AppContext context: Context): MoviesDatabase {
    return Room.databaseBuilder(context, MoviesDatabase::class.java, "movies_database").build()
  }
  
  @Singleton
  @Provides
  fun provideMoviesDao(db: MoviesDatabase) = db.getMoviesDao()
  
  @Singleton
  @Provides
  fun provideMovieDetailsDao(db: MoviesDatabase) = db.getMovieDetailsDao()
  
}