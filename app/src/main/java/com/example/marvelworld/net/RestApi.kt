package com.example.marvelworld.net

import com.example.marvelworld.model.Character
import com.example.marvelworld.model.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("/v1/public/characters")
    fun getTest(): Deferred<Character>

    @GET("/v1/public/characters")
    fun getAllCharacters(@Query("offset") offset: Int): Deferred<CharacterResponse>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int): Deferred<CharacterResponse>

    @GET("/v1/public/characters")
    fun getCharacterNameStartsWith(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("limit") limit: Int
    ): Deferred<CharacterResponse>
}