package com.chumachenko.core.extensions

import android.content.Context
import android.util.DisplayMetrics

fun dpToPixel(dp: Int, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}
