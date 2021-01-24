package com.w1tty.movies.data.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun parseDate(date: String): Date {
  val dfIn = SimpleDateFormat("yyyy-MM-dd", Locale.US)
  dfIn.timeZone = TimeZone.getTimeZone("UTC")
  return try {
    dfIn.parse(date) ?: Date()
  } catch (e: ParseException) {
    Date()
  }
}