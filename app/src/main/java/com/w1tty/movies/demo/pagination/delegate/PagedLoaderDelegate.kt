package com.w1tty.movies.demo.pagination.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.model.pagination.PagedListItem
import com.w1tty.movies.demo.model.pagination.PagedLoaderPm
import com.w1tty.movies.demo.utils.inflate

class PagedLoaderDelegate : AdapterDelegate<MutableList<PagedListItem>>() {
  
  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_paged_loader))
  }
  
  override fun isForViewType(items: MutableList<PagedListItem>, position: Int): Boolean {
    return items[position] is PagedLoaderPm
  }
  
  override fun onBindViewHolder(
    items: MutableList<PagedListItem>,
    position: Int,
    holder: RecyclerView.ViewHolder,
    payloads: MutableList<Any>
  ) {
  }
  
  private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}