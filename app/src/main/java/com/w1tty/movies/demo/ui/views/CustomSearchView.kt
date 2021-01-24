package com.w1tty.movies.demo.ui.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.utils.dimView
import com.w1tty.movies.demo.utils.forceHideKeyboard
import com.w1tty.movies.demo.utils.onClickThrottle
import com.w1tty.movies.demo.utils.showKeyboard
import kotlinx.android.synthetic.main.view_search_view.view.*

/**
 * Custom Search View layout with fade in / out animations
 */
class CustomSearchView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  
  companion object {
    private const val UPDATE_THRESHOLD = 300L
    private const val SUPER_STATE = "CustomSearchView.super.state"
    private const val SEARCH_STATE = "CustomSearchView.search.state"
  }
  
  private var runnable: Runnable? = null
  
  private var actionBarHeight = 0
  
  private var searchQuery: String = ""
  
  init {
    visibility = View.GONE
    LayoutInflater.from(context).inflate(R.layout.view_search_view, this, true)
    initHeight(context)
    subscribeChanges()
    initControls()
  }
  
  private fun initHeight(context: Context) {
    val tv = TypedValue()
    actionBarHeight = if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    } else {
      context.resources.getDimensionPixelSize(R.dimen.unused_action_bar_size)
    }
  }
  
  private fun subscribeChanges() {
    etSearch.doAfterTextChanged {
      setSearchQuery(it.toString().trim())
    }
  }
  
  private fun initControls() {
    btnCancelSearch.onClickThrottle {
      setSearchQuery("")
      callback?.onCloseSearchView()
    }
    
    btnClearSearch.onClickThrottle {
      setSearchQuery("")
      etSearch.text?.clear()
    }
    
    onClickThrottle { etSearch.requestFocus() }
    
    etSearch.setOnEditorActionListener { _, actionId, _ ->
      val searchAction = actionId == EditorInfo.IME_ACTION_SEARCH
      if (searchAction) {
        clearFocus()
      }
      searchAction
    }
  }
  
  var callback: Callback? = null
  
  override fun clearFocus() {
    etSearch.forceHideKeyboard()
    super.clearFocus()
  }
  
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec,
      MeasureSpec.makeMeasureSpec(actionBarHeight, MeasureSpec.EXACTLY))
  }
  
  override fun onSaveInstanceState(): Parcelable? {
    return bundleOf(
      SUPER_STATE to super.onSaveInstanceState(),
      SEARCH_STATE to searchQuery
    )
  }
  
  override fun onRestoreInstanceState(state: Parcelable?) {
    var superState = state
    (state as? Bundle)?.let {
      searchQuery = it.getString(SEARCH_STATE).orEmpty()
      superState = it.getParcelable(SUPER_STATE)
    }
    super.onRestoreInstanceState(superState)
  }
  
  fun setHint(resId: Int) {
    etSearch.setHint(resId)
  }
  
  fun openSearchView(fast: Boolean = false) {
    super.setVisibility(View.VISIBLE)
    
    if (fast) {
      alpha = 1f
    } else {
      dimView(true)
    }
    
    etSearch.requestFocus()
  }
  
  fun showKeyboard(delay: Long = 0L) {
    etSearch.showKeyboard(delay)
  }
  
  fun closeSearchView() {
    if (!isOpened()) {
      return
    }
    
    val fade = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f)
    fade.addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator?) {
        visibility = View.GONE
      }
    })
    
    dimView(false)
    
    etSearch.text?.clear()
  }
  
  fun isOpened() = visibility == View.VISIBLE
  
  private fun setSearchQuery(query: String) {
    runnable?.let { handler.removeCallbacks(it) }
    runnable = Runnable {
      if (query != searchQuery) {
        searchQuery = query
        callback?.onQueryTextChanged(query)
      }
    }.apply {
      handler.postDelayed(this, UPDATE_THRESHOLD)
    }
    btnClearSearch.isVisible = query.isNotBlank()
  }
  
  interface Callback {
    fun onQueryTextChanged(query: String)
    fun onCloseSearchView()
  }
}