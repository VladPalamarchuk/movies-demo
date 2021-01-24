package com.w1tty.movies.utility.extension

import com.w1tty.movies.utility.model.DiffEquals

/**
 * Compares two objects by their parameters. The [properties] must
 * always be the properties of the [T] class. Otherwise, this function
 * will fail due to [UNCHECKED_CAST].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> T.equalTo(other: Any?, vararg properties: T.() -> Any?): Boolean {
  if (other !is T) return false
  properties.forEach {
    val currentValue = it.invoke(this)
    val otherValue = it.invoke(other)
    if (currentValue is List<*>) {
      if (otherValue !is List<*> || !currentValue.isListContentEqualsOrSame(otherValue)) {
        return false
      }
    } else if (it.invoke(this) != it.invoke(other)) {
      return false
    }
  }
  return true
}

/**
 * Compare two lists by their content.
 * This method uses a default equal operation to compare list items
 * or the diff equals mechanic if the list item is implements [DiffEquals]
 * @return true when same, false otherwise
 */
fun List<*>.isListContentEqualsOrSame(other: List<*>): Boolean {
  if (size != other.size) return false
  withIndex().forEach {
    if (it.value is DiffEquals && other[it.index] is DiffEquals) {
      if (!(it.value as DiffEquals).isContentSame(other[it.index])) {
        return false
      }
    } else if (it.value != other[it.index]) {
      return false
    }
  }
  return true
}