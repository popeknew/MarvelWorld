package com.example.marvelworld.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.marvelworld.ext.createPictureUrl
import com.example.marvelworld.model.Character
import com.example.marvelworld.model.PictureSize
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.errorText(text: String?) {
    errorIconDrawable = null
    error = text
}

@BindingAdapter("setAvatar")
fun ImageView.setAvatar(character: Character) {
    val url = createPictureUrl(character, PictureSize.PORTRAIT_MEDIUM)
    Glide.with(this)
        .load(url)
        .into(this)
}