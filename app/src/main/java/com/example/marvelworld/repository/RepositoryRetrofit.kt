package com.example.marvelworld.repository

import com.example.marvelworld.model.Character
import com.example.marvelworld.model.CharacterResponse
import com.example.marvelworld.net.RestApi

class RepositoryRetrofit(private val api: RestApi) {

    suspend fun getTest() : Character = api.getTest().await()

    suspend fun getAllCharacters(): CharacterResponse = api.getAllCharacters().await()
}