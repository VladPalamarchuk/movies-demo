package com.w1tty.movies.demo.utils

import androidx.recyclerview.widget.DiffUtil
import com.w1tty.movies.demo.model.pagination.PagedListItem

class PagedListDiffCallback(
  private val newItems: List<PagedListItem>,
  private val oldItems: List<PagedListItem>
) : DiffUtil.Callback() {
  
  override fun getOldListSize() = oldItems.size
  override fun getNewListSize() = newItems.size
  
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return newItems[newItemPosition].isItemSame(oldItems[oldItemPosition])
  }
  
  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return newItems[newItemPosition].isContentSame(oldItems[oldItemPosition])
  }
}