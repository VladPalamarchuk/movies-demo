package com.w1tty.movies.demo.ui.screens.movies.list.delegate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.model.MoviePm
import com.w1tty.movies.demo.model.pagination.PagedListItem
import com.w1tty.movies.demo.ui.screens.movies.list.MoviesAdapter
import com.w1tty.movies.demo.utils.glideLoadPicture
import com.w1tty.movies.demo.utils.inflate
import com.w1tty.movies.demo.utils.onClickThrottle
import kotlinx.android.synthetic.main.list_item_movie.view.*
import javax.inject.Inject

class MovieDelegate @Inject constructor() : AdapterDelegate<MutableList<PagedListItem>>() {
  
  var callback: MoviesAdapter.Callback? = null
  
  override fun isForViewType(items: MutableList<PagedListItem>, position: Int): Boolean {
    return items[position] is MoviePm
  }
  
  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_movie))
  }
  
  override fun onBindViewHolder(
    items: MutableList<PagedListItem>,
    position: Int,
    holder: RecyclerView.ViewHolder,
    payloads: MutableList<Any>
  ) {
    val item = items[position] as MoviePm
    holder as ViewHolder
    holder.bind(item)
  }
  
  private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: MoviePm) {
      with(itemView) {
        tvTitle.text = item.title
        ivPicture.glideLoadPicture(item.picture)
        onClickThrottle { callback?.onMovieClicked(item) }
      }
    }
  }
}