package com.w1tty.movies.demo.utils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.core.view.ViewPropertyAnimatorListener

fun View.dimView(show: Boolean, duration: Long = 250, endListener: (() -> Unit)? = null,
  delay: Long = 0): ViewPropertyAnimatorCompat {
  isEnabled = show
  if (show) alpha = 0f
  val animator = ViewCompat.animate(this)
  animator.alpha(if (show) 1f else 0f)
    .setDuration(duration)
    .setStartDelay(delay)
    .setListener(object : ViewPropertyAnimatorListener {
      override fun onAnimationEnd(view: View) {
        view.visibility = if (show) View.VISIBLE else View.GONE
        alpha = 1f
        endListener?.invoke()
      }
  
      override fun onAnimationCancel(view: View) {
        view.visibility = View.GONE
      }
  
      override fun onAnimationStart(view: View) {
        view.visibility = View.VISIBLE
      }
    })
    .withLayer()
    .start()
  return animator
}
