package com.example.marvelworld.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("name ")
    val name: String
)