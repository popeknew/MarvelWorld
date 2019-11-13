package com.example.marvelworld.repository

import com.example.marvelworld.net.RestApi

class RepositoryRetrofit(private val api: RestApi) {

    suspend fun getTest() : List<String> = api.getTest().await()
}