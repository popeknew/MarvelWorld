package com.example.marvelworld.ext

import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.example.marvelworld.model.Character
import com.example.marvelworld.model.PictureSize

fun createPictureUrl(character: Character, pictureSize: PictureSize): String {
    val path = character.thumbnail.path
    val extension = character.thumbnail.extension
    val newPath = "$path/${pictureSize.name.toLowerCase()}.$extension"
    return newPath.changeToSecureUrl()
}

fun AppCompatEditText.setRightDrawableOnTouchListener(func: AppCompatEditText.() -> Unit) {
    setOnTouchListener { _, event ->
        var consumed = false
        if (event.action == MotionEvent.ACTION_UP) {
            val drawable = compoundDrawables[2]
            if (event.rawX >= (right - drawable.bounds.width())) {
                func()
                consumed = true
            }
        }
        consumed
    }
}