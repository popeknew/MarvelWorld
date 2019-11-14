package com.example.marvelworld.model

import com.google.gson.annotations.SerializedName

data class CharacterImage(
    @SerializedName("path")
    val path: String
)