package com.w1tty.movies.demo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), HasAndroidInjector {
  
  private var isViewModelFirstAttach: Boolean = false
  
  @Inject
  lateinit var androidInjector: DispatchingAndroidInjector<Any>
  
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  
  abstract val viewModel: VM
  
  abstract val layoutResId: Int
  
  abstract fun initViews()
  
  override fun onAttach(context: Context) {
    super.onAttach(context)
    AndroidSupportInjection.inject(this)
  }
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layoutResId, container, false)
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
    viewModelFirstAttach()
  }
  
  private fun viewModelFirstAttach() {
    if (!isViewModelFirstAttach) {
      isViewModelFirstAttach = true
      viewModel.onFirstAttach()
    }
  }
  
  override fun androidInjector(): AndroidInjector<Any> {
    return androidInjector
  }
  
  inline fun <reified T : BaseViewModel> provideViewModel(): Lazy<T> {
    return lazy {
      // Need to wait [viewModelFactory] injection
      ViewModelProvider(this, viewModelFactory)[T::class.java]
    }
  }
}