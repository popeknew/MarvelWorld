package com.example.marvelworld.net

import com.example.marvelworld.model.Character
import com.example.marvelworld.model.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("/v1/public/characters")
    fun getTest() : Deferred<Character>

    @GET("/v1/public/characters")
    fun getAllCharacters(): Deferred<CharacterResponse>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int): Deferred<CharacterResponse>
}