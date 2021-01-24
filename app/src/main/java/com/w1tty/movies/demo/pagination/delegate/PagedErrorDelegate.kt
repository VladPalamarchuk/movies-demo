package com.w1tty.movies.demo.pagination.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.model.pagination.PagedErrorPm
import com.w1tty.movies.demo.model.pagination.PagedListItem
import com.w1tty.movies.demo.utils.inflate
import kotlinx.android.synthetic.main.list_item_paged_error.view.*

class PagedErrorDelegate : AdapterDelegate<MutableList<PagedListItem>>() {
  
  var retryCallback: (() -> Unit)? = null
  
  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_paged_error))
  }
  
  override fun isForViewType(items: MutableList<PagedListItem>, position: Int): Boolean {
    return items[position] is PagedErrorPm
  }
  
  override fun onBindViewHolder(
    items: MutableList<PagedListItem>,
    position: Int,
    holder: RecyclerView.ViewHolder,
    payloads: MutableList<Any>
  ) {
    (holder as ViewHolder).bind()
  }
  
  private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind() {
      with(itemView) {
        btnRetry.setOnClickListener {
          retryCallback?.invoke()
        }
      }
    }
  }
}