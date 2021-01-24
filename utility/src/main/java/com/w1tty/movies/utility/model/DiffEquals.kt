package com.w1tty.movies.utility.model

interface DiffEquals {

  fun isItemSame(other: Any?): Boolean

  fun isContentSame(other: Any?): Boolean

}