package com.w1tty.movies.demo.model

import android.os.Parcelable
import com.w1tty.movies.demo.model.pagination.PagedListItem
import com.w1tty.movies.utility.extension.equalTo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviePm(
  val id: String,
  val title: String,
  val picture: String?
) : PagedListItem, Parcelable {
  override fun isItemSame(other: Any?): Boolean {
    return equalTo(other, { id })
  }
  
  override fun isContentSame(other: Any?): Boolean {
    return equalTo(other, { title }, { picture })
  }
}