package com.w1tty.movies.utility.mapper

import java.util.*

abstract class BaseMapper<From, To> {

  abstract fun map(from: From): To

  open fun mapList(froms: List<From>): List<To> {
    return ArrayList<To>(froms.size).apply {
      froms.forEach {
        add(map(it))
      }
    }
  }
}