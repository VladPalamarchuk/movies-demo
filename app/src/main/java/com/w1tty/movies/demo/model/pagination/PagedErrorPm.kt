package com.w1tty.movies.demo.model.pagination

import com.w1tty.movies.utility.extension.equalTo

class PagedErrorPm : PagedListItem {

  override fun isItemSame(other: Any?): Boolean {
    return equalTo(other)
  }

  override fun isContentSame(other: Any?): Boolean {
    return equalTo(other)
  }
}