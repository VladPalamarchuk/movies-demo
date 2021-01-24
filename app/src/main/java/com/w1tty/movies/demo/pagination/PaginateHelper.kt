package com.w1tty.movies.demo.pagination

import javax.inject.Inject

class PaginateHelper @Inject constructor() {
  
  private var currentPage: Int = 1
  private var nextPage = 1
  private var endReached: Boolean = false
  
  fun isFirstPage() = currentPage == 1
  
  fun getCurrentPage() = currentPage
  
  fun isNextPageReadyToLoad(): Boolean {
    val canLoad = currentPage != nextPage && !endReached
    if (canLoad) {
      currentPage = nextPage
    }
    return canLoad
  }
  
  fun notifyPageLoaded(size: Int) {
    endReached = size == 0
    nextPage++
  }
  
  fun restart() {
    currentPage = 1
    nextPage = 1
    endReached = false
  }
}