package com.chumachenko.core.extensions

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.nio.channels.NotYetBoundException

fun dpToPixel(dp: Int, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun RecyclerView.getCurrentPosition(): Int =
    (this.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

public fun <T> List<T>.second(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    if (size < 1)
        throw NotYetBoundException()
    return this[1]
}