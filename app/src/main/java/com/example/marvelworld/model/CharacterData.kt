package com.example.marvelworld.model

import com.google.gson.annotations.SerializedName

data class CharacterData(
    @SerializedName("results")
    val results: List<Character>
)