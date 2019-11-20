package com.example.marvelworld.model

import com.google.gson.annotations.SerializedName

data class CharacterData(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<Character>
)