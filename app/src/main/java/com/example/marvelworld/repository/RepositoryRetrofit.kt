package com.example.marvelworld.repository

import com.example.marvelworld.model.CharacterResponse
import com.example.marvelworld.net.RestApi

class RepositoryRetrofit(private val api: RestApi) {

    suspend fun getAllCharacters(offset: Int): CharacterResponse = api.getAllCharacters(offset).await()
    suspend fun getCharacter(characterId: Int): CharacterResponse = api.getCharacter(characterId).await()
    suspend fun getCharacterNameStartsWith(text: String): CharacterResponse = api.getCharacterNameStartsWith(text).await()
}