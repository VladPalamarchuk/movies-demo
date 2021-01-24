package com.w1tty.movies.demo.ui.screens.movies.list

import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.demo.pagination.adapter.PagedAsyncAdapter
import com.w1tty.movies.demo.ui.screens.movies.list.delegate.MovieDelegate
import javax.inject.Inject

class MoviesAdapter @Inject constructor(
  private val moviesDelegate: MovieDelegate
) : PagedAsyncAdapter() {
  init {
    delegatesManager.addDelegate(moviesDelegate)
  }
  
  var callback: Callback? = null
    set(value) {
      field = value
      moviesDelegate.callback = value
    }
  
  interface Callback {
    fun onMovieClicked(item: MoviePm)
  }
}