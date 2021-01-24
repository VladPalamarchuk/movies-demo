package com.w1tty.movies.demo.utils

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.ui.views.CustomSearchView

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
  LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

/**
 * Ignore the fast series of clicks to prevent multiple action calls.
 *
 * @param call - a click listener
 * @return disposable of rx click method. Should be disposed with view destructor
 */
fun View.onClickThrottle(delay: Long = 500L, call: () -> Unit) {
  setOnClickListener(object : View.OnClickListener {
    var time = 0L
    override fun onClick(v: View?) {
      val newTime = System.currentTimeMillis()
      if (newTime - time > delay) {
        call.invoke()
        time = newTime
      }
    }
  })
}

/**
 * Get action bar height from device theme or just take the backup
 * height when the device has no actionBarSize attribute.
 */
fun View?.fetchActionBarHeight(): Int {
  val tv = TypedValue()
  var actionBarHeight = 0
  if (this != null) {
    actionBarHeight = if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv,
        true) && !isInEditMode) {
      TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    } else {
      context.pxDimen(R.dimen.unused_action_bar_size)
    }
  }
  return actionBarHeight
}

fun Context?.pxDimen(@DimenRes dimenRes: Int): Int {
  return this?.resources?.getDimensionPixelSize(dimenRes) ?: 0
}

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun ImageView.glideLoadPicture(path: String?) {
  if (path.isNullOrBlank()) {
    setImageDrawable(null)
  } else {
    Glide.with(this)
      .load(path)
      .into(this)
  }
}

/**
 * Show a keyboard using an Edit text to bind focus
 */
fun AppCompatEditText.showKeyboard(delay: Long = 0L, listener: (() -> Unit)? = null) {
  requestFocus()
  
  val handler = Handler(Looper.getMainLooper())
  
  val receiver: ResultReceiver? = if (listener != null) {
    object : ResultReceiver(handler) {
      override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        if (resultCode == InputMethodManager.RESULT_SHOWN
          || resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN) {
          postDelayed({
            listener.invoke()
          }, 300)
        }
      }
    }
  } else {
    null
  }
  val inputMethodManager = context?.getSystemService(
    Context.INPUT_METHOD_SERVICE) as? InputMethodManager
  
  handler.postDelayed({
    inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_FORCED, receiver)
  }, delay)
}

/**
 * Force close a keyboard whether the window has focus (on some view) or not
 */
fun AppCompatEditText.forceHideKeyboard() {
  (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
    ?.hideSoftInputFromWindow(windowToken, 0)
}


fun CustomSearchView.adjustStateWith(
  isOpened: Boolean,
  recycler: RecyclerView
) {
  if (isOpened() != isOpened) {
    if (isOpened) {
      openSearchView()
      showKeyboard()
    } else {
      recycler.scrollToPosition(0)
      closeSearchView()
    }
  }
}


/**
 * Find a view with focus and force close the keyboard in fragment
 * if you provide listener, than keyboard will be hidden and action will be called after delay
 * delay is introduced due to immediate result of ResultReceiver.
 * It was stated that keyboard will never animate longer than 200ms (unconfirmed)
 */
fun Fragment.hideKeyboard(listener: (() -> Unit)? = null) {
  (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let { im ->
    view?.findFocus()?.let {
      val receiver = if (listener != null) {
        object : ResultReceiver(Handler(requireContext().mainLooper)) {
          override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            if (resultCode == InputMethodManager.RESULT_HIDDEN) {
              view?.postDelayed({
                listener.invoke()
              }, 250)
            }
          }
        }
      } else {
        null
      }
      im.hideSoftInputFromWindow(it.windowToken, 0, receiver)
    }
  }
}
