package com.heybeach.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager

@Suppress("DEPRECATION")
fun String?.createHtmlText(): CharSequence {
    return this?.run {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(this)
        }
    } ?: ""
}

fun Context.getLayoutInflater() = LayoutInflater.from(this)!!

fun Context.getInputManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun View.closeSoftKeyboard() {
    context.getInputManager().hideSoftInputFromWindow(windowToken, 0)
}

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}
