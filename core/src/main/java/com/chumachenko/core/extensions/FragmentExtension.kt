package com.chumachenko.core.extensions

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.chumachenko.core.common.InsetHolder

fun Fragment.setConstraintStatusBarHeight(view: View) {
    val statusParams = view.layoutParams as ConstraintLayout.LayoutParams
    statusParams.height = (requireActivity() as InsetHolder).topInset
    view.layoutParams = statusParams
}