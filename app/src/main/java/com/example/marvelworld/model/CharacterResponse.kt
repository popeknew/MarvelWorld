package com.example.marvelworld.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: CharacterData
)