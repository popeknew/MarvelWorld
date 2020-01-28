package com.example.marvelworld.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.marvelworld.R

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, resources.getInteger(R.integer.zero))
}