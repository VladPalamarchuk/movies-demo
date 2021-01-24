package com.w1tty.movies.demo.utils

import java.text.SimpleDateFormat
import java.util.*

// Returns 21:59
fun parseTimeFromDate(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("HH:mm", locale)
  return dfOut.format(date)
}

// Returns July or August or...
fun parseMonthFromDate(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("MMMM", locale)
  return dfOut.format(date)
}

// Returns 2019 or 2020 or...
fun parseYearFromDate(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("YYYY", locale)
  return dfOut.format(date)
}

// Full date

// Returns 23 September 2019
fun parseFullDate(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd MMMM yyyy", locale)
  return dfOut.format(date)
}

// Returns 23 September
fun parseFullDateWithoutYear(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd MMMM", locale)
  return dfOut.format(date)
}

// Returns September 2020
fun parseFullDateWithoutDay(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("MMMM yyyy", locale)
  return dfOut.format(date)
}

// Simple date

// Returns 23 Sep
fun parseSimpleDateWithoutYear(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd MMM", locale)
  return dfOut.format(date)
}

// Returns either 23 Sep 2019 or 23 Sep, depends on current year
fun parseSimpleDateChooseYear(locale: Locale, date: Date): String {
  val calendar = Calendar.getInstance()
  val currentYear = calendar.get(Calendar.YEAR)
  
  calendar.time = date
  val incomingYear = calendar.get(Calendar.YEAR)
  
  val pattern = if (incomingYear != currentYear) "dd MMM yyyy" else "dd MMM"
  val dfOut = SimpleDateFormat(pattern, locale)
  return dfOut.format(date)
}

// Returns either 23 September 2019 or 23 September, depends on current year
fun parseFullDateChooseYear(locale: Locale, date: Date): String {
  val calendar = Calendar.getInstance()
  val currentYear = calendar.get(Calendar.YEAR)
  
  calendar.time = date
  val incomingYear = calendar.get(Calendar.YEAR)
  
  val pattern = if (incomingYear != currentYear) "dd MMMM yyyy" else "dd MMMM"
  val dfOut = SimpleDateFormat(pattern, locale)
  return dfOut.format(date)
}

// Returns either 23 Sep '19 or 23 Sep, depends on current year
fun parseSimpleDateChooseSimpleYear(locale: Locale, date: Date): String {
  val calendar = Calendar.getInstance()
  val currentYear = calendar.get(Calendar.YEAR)
  
  calendar.time = date
  val incomingYear = calendar.get(Calendar.YEAR)
  
  val dfOut = SimpleDateFormat("d MMM", locale)
  val months = dfOut.format(date)
  return if (incomingYear != currentYear) {
    val dfOut2 = SimpleDateFormat("yy", locale)
    val year = dfOut2.format(date)
    "$months '$year"
  } else months
}

// Returns 23 Sep 2019 in device time zone
fun parseSimpleDate(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd MMM yyyy", locale)
  return dfOut.format(date)
}

// Returns 11:21 in device time zone
fun parseSimpleTime(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("HH:mm", locale)
  return dfOut.format(date)
}

fun parseHoursMinutes(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("mm:ss", locale)
  return dfOut.format(date)
}

// Returns 23 Sep 2019 in UTC
fun parseSimpleDateInUtc(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd MMM yyyy", locale)
  dfOut.timeZone = TimeZone.getTimeZone("UTC")
  return dfOut.format(date)
}

// Returns 11:21 in UTC
fun parseSimpleTimeInUtc(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("HH:mm", locale)
  dfOut.timeZone = TimeZone.getTimeZone("UTC")
  return dfOut.format(date)
}

fun parseBirthDate(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd.MM.yyyy", locale)
  dfOut.timeZone = TimeZone.getTimeZone("UTC")
  return dfOut.format(date)
}

// Returns 23 Sep 2019 21:59
fun parseSimpleDateTime(locale: Locale, date: Date): String {
  val dfOut = SimpleDateFormat("dd MMM yyyy HH:mm", locale)
  return dfOut.format(date)
}

fun Date.isDateTheSame(other: Date): Boolean {
  val c = Calendar.getInstance()
  c.time = this
  val year = c.get(Calendar.YEAR)
  val month = c.get(Calendar.MONTH)
  val day = c.get(Calendar.DAY_OF_YEAR)
  c.time = other
  return year == c.get(Calendar.YEAR) && month == c.get(Calendar.MONTH) &&
    day == c.get(Calendar.DAY_OF_YEAR)
}

fun Date.isSameDay(other: Date): Boolean {
  val c = Calendar.getInstance()
  c.time = this
  val day = c.get(Calendar.DAY_OF_YEAR)
  c.time = other
  return day == c.get(Calendar.DAY_OF_YEAR)
}

fun Date.isSameYear(other: Date): Boolean {
  val c = Calendar.getInstance()
  c.time = this
  val day = c.get(Calendar.YEAR)
  c.time = other
  return day == c.get(Calendar.YEAR)
}

fun Date.isDayLessThan(other: Date): Boolean {
  val c = Calendar.getInstance()
  c.time = this
  val currentDay = c.get(Calendar.DAY_OF_YEAR)
  val currentYear = c.get(Calendar.YEAR)
  c.time = other
  val compareDay = c.get(Calendar.DAY_OF_YEAR)
  val compareYear = c.get(Calendar.YEAR)
  return when {
    currentYear < compareYear -> true
    currentYear == compareYear -> currentDay < compareDay
    else -> false
  }
}
