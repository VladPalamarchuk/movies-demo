package com.w1tty.movies.demo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.utils.onClickThrottle
import kotlinx.android.synthetic.main.layout_state_holder.view.*

class StateHolderLayout @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  
  var retryCallback: (() -> Unit)? = null
  
  init {
    LayoutInflater.from(context).inflate(R.layout.layout_state_holder, this)
    btnRetry.onClickThrottle { retryCallback?.invoke() }
  }
  
  fun setStateOrNothing(state: State, isActive: Boolean) {
    if (isActive) {
      setState(state)
    } else {
      setState(State.NOTHING)
    }
  }
  
  fun setEmptyText(@StringRes res: Int) {
    emptyState.setText(res)
  }
  
  fun setState(state: State) {
    // hide root view when nothing
    isVisible = state != State.NOTHING
    
    errorState.isVisible = state == State.ERROR
    progressState.isVisible = state == State.LOADING
    emptyState.isVisible = state == State.EMPTY
  }
  
  enum class State {
    NOTHING,
    LOADING,
    ERROR,
    EMPTY
  }
}