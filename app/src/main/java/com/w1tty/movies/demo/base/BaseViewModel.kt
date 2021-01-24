package com.w1tty.movies.demo.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
  open fun onFirstAttach() {
  
  }
}