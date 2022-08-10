package com.chumachenko.core.common


interface InsetHolder {
    val topInset: Int
    val bottomInset: Int
    fun onKeyboardEvent(imeVisible: Boolean, imeHeight: Int){}
}