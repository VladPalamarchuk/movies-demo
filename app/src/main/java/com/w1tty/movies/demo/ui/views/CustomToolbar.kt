package com.w1tty.movies.demo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.w1tty.movies.demo.R
import com.w1tty.movies.demo.utils.fetchActionBarHeight
import com.w1tty.movies.demo.utils.onClickThrottle
import com.w1tty.movies.demo.utils.pxDimen
import kotlinx.android.synthetic.main.layout_toolbar.view.*

/**
 * This is custom toolbar with custom title.
 *
 * @attr title - shows the toolbar title
 * @attr description - shows the toolbar description text
 * @attr navigation_icon - shows the left side icon and automatically hide the left side text button
 * @attr navigation_text - same as icon, but use a text button
 * @attr options_icon - shows the right side icon
 * @attr options_text - shows the left side optional text button
 * @attr gravity_center - set the title and description gravity to center, default is start
 * @attr show_divider - shows/hides the divider
 *
 * When some of this parameters aren't specified, the accordingly views will be hidden.
 * Caution: Toolbar has default elevation and default color.
 */
class CustomToolbar @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  
  private val maxOffset = 80
  private var maxElevation: Int = 0
  private var optionButton: View? = null
  
  private val actionBarHeight = fetchActionBarHeight()
  
  var navigationClickListener: (() -> Unit)? = null
  var optionsClickListener: (() -> Unit)? = null
  
  init {
    View.inflate(context, R.layout.layout_toolbar, this)
    
    // Setup layout default view
    maxElevation = context.pxDimen(R.dimen.default_elevation)
    
    // Setup attributes
    setupAttributes(attrs)
  }
  
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(
      widthMeasureSpec,
      MeasureSpec.makeMeasureSpec(actionBarHeight, MeasureSpec.EXACTLY)
    )
  }
  
  @SuppressWarnings("ResourceType")
  private fun setupAttributes(attrs: AttributeSet?) {
    attrs?.let {
      val a = context.obtainStyledAttributes(it, R.styleable.CustomToolbar)
      
      // Setup navigation
      val navIcon = a.getResourceId(R.styleable.CustomToolbar_toolbar_navigation_icon, 0)
      if (navIcon != 0) {
        btnNavigation.isVisible = true
        btnNavigation.setImageResource(navIcon)
        btnNavigation.onClickThrottle { navigationClickListener?.invoke() }
      }
      
      // Setup title
      val titleString = a.getResourceId(R.styleable.CustomToolbar_toolbar_title, 0)
      if (titleString != 0) {
        baseDialogTvTitle.isVisible = true
        baseDialogTvTitle.text = context.getString(titleString)
      }
      
      val descString = a.getResourceId(R.styleable.CustomToolbar_toolbar_description, 0)
      if (descString != 0) {
        tvDescription.isVisible = true
        tvDescription.text = context.getString(descString)
      }
      
      // Setup option button
      val optionIcon = a.getResourceId(R.styleable.CustomToolbar_toolbar_options_icon, 0)
      if (optionIcon != 0) {
        setOptionButton(optionIcon)
      }
      
      a.recycle()
      
      // Parse Android attribute
      val androidAttr = intArrayOf(android.R.attr.background, android.R.attr.elevation)
      
      val b = context.obtainStyledAttributes(it, androidAttr)
      
      // Set elevation
      val el = b.getDimensionPixelSize(1, -1).toFloat()
      elevation = if (el < 0) 0f else el
      
      b.recycle()
    }
  }
  
  /**
   * Set optional icon and show the optional button if it was hidden.
   * The text optional button will be hidden.
   *
   * @param resId - drawable link
   */
  fun setOptionButton(@DrawableRes resId: Int) {
    btnOptions.isVisible = true
    btnOptions.setImageResource(resId)
    btnOptions.onClickThrottle { optionsClickListener?.invoke() }
    
    optionButton = btnOptions
  }
  
}