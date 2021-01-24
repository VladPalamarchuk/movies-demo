package com.w1tty.movies.demo.pagination.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.w1tty.movies.demo.model.pagination.PagedErrorPm
import com.w1tty.movies.demo.model.pagination.PagedListItem
import com.w1tty.movies.demo.model.pagination.PagedLoaderPm
import com.w1tty.movies.demo.pagination.PagedAdapterState
import com.w1tty.movies.demo.pagination.PagedAdapterStateItemsCallback
import com.w1tty.movies.demo.pagination.delegate.PagedErrorDelegate
import com.w1tty.movies.demo.pagination.delegate.PagedLoaderDelegate
import com.w1tty.movies.demo.utils.PagedListDiffCallback
import kotlinx.coroutines.*

abstract class PagedAsyncAdapter : ListDelegationAdapter<MutableList<PagedListItem>>(), PagedAdapterStateItemsCallback {
  
  private val errorDelegate = PagedErrorDelegate()
  private val loaderDelegate = PagedLoaderDelegate()
  
  private var pendingNextState: PagedAdapterState? = null
  
  var retryLoadCallback: (() -> Unit)? = null
    set(value) {
      field = value
      errorDelegate.retryCallback = {
        field?.invoke()
      }
    }
  
  init {
    items = mutableListOf()
    delegatesManager
      .addDelegate(errorDelegate)
      .addDelegate(loaderDelegate)
  }
  
  private val pagedLoader = PagedLoaderPm()
  private val pagedError = PagedErrorPm()
  
  private var updatingJob: Job? = null
  
  fun updateData(newItems: List<PagedListItem>, scope: CoroutineScope) {
    // avoid processing diff utils when list is empty
    // occurs when config changes occur
    // the recycler jumps to the top in this case
    if (items.isEmpty()) {
      items.addAll(newItems)
      notifyDataSetChanged()
    } else {
      updatingJob?.cancel()
      updatingJob = scope.launch {
        val result = withContext(Dispatchers.Default) {
          DiffUtil.calculateDiff(PagedListDiffCallback(newItems, items))
        }
        dispatchUpdates(newItems, result)
      }
    }
  }
  
  private fun dispatchUpdates(newItems: List<PagedListItem>, diffResult: DiffUtil.DiffResult) {
    items.clear()
    items.addAll(newItems)
    diffResult.dispatchUpdatesTo(this)
    
    updatingJob = null
    
    pendingNextState?.let {
      showNextPageState(it)
      pendingNextState = null
    }
  }
  
  override fun showNextPageState(state: PagedAdapterState) {
    if (updatingJob != null) {
      pendingNextState = state
      return
    }
    
    val lastIndex = items.lastIndex
    val data = items.toMutableList()
    when (state) {
      PagedAdapterState.PROGRESS -> {
        showNextPageProgress(data, lastIndex)
      }
      PagedAdapterState.ERROR -> {
        showNextPageError(data, lastIndex)
      }
      PagedAdapterState.NOTHING -> {
        showNextPageNothing(data, lastIndex)
      }
    }
  }
  
  private fun showNextPageNothing(
    data: MutableList<PagedListItem>,
    lastIndex: Int
  ) {
    if (isNextError() || isNextProgress()) {
      data.removeAt(lastIndex)
      items = data
      notifyItemRemoved(lastIndex)
    }
  }
  
  private fun showNextPageError(
    data: MutableList<PagedListItem>,
    lastIndex: Int
  ) {
    if (!isNextError()) {
      val nextProgress = isNextProgress()
      if (nextProgress) {
        data.removeAt(lastIndex)
      }
      data.add(pagedError)
      items = data
      if (nextProgress) {
        notifyItemChanged(lastIndex)
      } else {
        notifyItemInserted(lastIndex + 1)
      }
    }
  }
  
  private fun showNextPageProgress(
    data: MutableList<PagedListItem>,
    lastIndex: Int
  ) {
    if (!isNextProgress()) {
      val nextError = isNextError()
      if (nextError) {
        data.removeAt(lastIndex)
      }
      data.add(pagedLoader)
      items = data
      if (nextError) {
        notifyItemChanged(lastIndex)
      } else {
        notifyItemInserted(lastIndex + 1)
      }
    }
  }
  
  private fun isNextProgress() = items.isNotEmpty() && items.last() is PagedLoaderPm
  
  private fun isNextError() = items.isNotEmpty() && items.last() is PagedErrorPm
  
  private fun isPreviousProgress() = items.isNotEmpty() && items.first() is PagedLoaderPm
  
  private fun isPreviousError() = items.isNotEmpty() && items.first() is PagedErrorPm
  
  fun hideStates() {
    showNextPageState(PagedAdapterState.NOTHING)
  }
  
  fun destroy() {
//    disposable.dispose()
  }
}