package com.example.marvelworld.ext

import com.example.marvelworld.model.Character
import com.example.marvelworld.model.PictureSize

fun createPictureUrl(character: Character, pictureSize: PictureSize): String {
    val path = character.thumbnail.path
    val extension = character.thumbnail.extension
    val newPath = "$path/${pictureSize.name.toLowerCase()}.$extension"
    return newPath.changeToSecureUrl()
}