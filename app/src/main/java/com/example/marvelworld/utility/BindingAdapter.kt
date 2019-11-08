package com.example.marvelworld.utility

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.errorText(text: String?) {
    errorIconDrawable = null
    error = text
}